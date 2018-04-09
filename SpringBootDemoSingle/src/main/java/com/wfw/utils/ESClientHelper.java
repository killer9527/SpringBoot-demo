package com.wfw.utils;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by killer9527 on 2018/4/4.
 * es客户端工具类
 */
@Component
public class ESClientHelper {
    private static Environment env;

    @Autowired
    public void setEnv(Environment env) {
        ESClientHelper.env = env;
    }

    private static boolean isInit = false;

    /**
     * 集群对应的客户端，key为clusterName
     */
    private static Map<String, Client> clientMap = new ConcurrentHashMap<>();

    /**
     * 初始化默认的client
     */
    private static void init() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");

        //集群1名称及es地址
        String clusterName1 = ESClientHelper.env.getProperty("elasticsearch.cluster-name1");
        String cluster1nodes = ESClientHelper.env.getProperty("elasticsearch.cluster1-nodes");
        addClient(clusterName1, cluster1nodes);

        //集群2名称及es地址
        String clusterName2 = ESClientHelper.env.getProperty("elasticsearch.cluster-name2");
        String cluster2nodes = ESClientHelper.env.getProperty("elasticsearch.cluster2-nodes");
        addClient(clusterName2, cluster2nodes);
    }

    /**
     * 获得所有的地址端口
     *
     * @return
     */
    private static List<TransportAddress> getAllAddress(Map<String, Integer> ips) {
        try {
            List<TransportAddress> addressList = new ArrayList<>();
            for (String ip : ips.keySet()) {
                TransportAddress address = new TransportAddress(InetAddress.getByName(ip), ips.get(ip));
                addressList.add(address);
            }
            return addressList;
        } catch (Exception ex) {
            System.err.println("Initial ESClient creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * 获取指定集群的es客户端
     *
     * @param clusterName
     * @return
     */
    public static Client getClient(String clusterName) {
        if (!isInit) {
            init();
            isInit = true;
        }
        return clientMap.get(clusterName);
    }

    /**
     * 为其他集群添加客户端
     *
     * @param setting 配置信息
     * @param ips     node-port列表，例如：127.0.0.1-9300
     */
    private static void addClient(Settings setting, Map<String, Integer> ips) {
        List<TransportAddress> transportAddress = getAllAddress(ips);

        Client client = new PreBuiltTransportClient(setting)
                .addTransportAddresses(transportAddress.toArray(new TransportAddress[transportAddress.size()]));
        clientMap.put(setting.get("cluster.name"), client);
    }

    /**
     * 为其他集群添加客户端
     *
     * @param clusterName   集群名
     * @param strConnection 集群地址
     */
    public static void addClient(String clusterName, String strConnection) {
        String[] nodeConnections = strConnection.split(";|,");
        Map<String, Integer> ips = new HashMap<>();
        for (String nodeConnection : nodeConnections) {
            Pattern pattern = Pattern.compile("(http://)*([.\\d]+):(\\d{1,6})");
            Matcher matcher = pattern.matcher(nodeConnection);
            if (matcher.find()) {
                try {
                    String hostname = matcher.group(2);
                    int port = Integer.parseInt(matcher.group(3));
                    ips.put(hostname, port);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        Settings setting = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.ignore_cluster_name", false)
                .put("client.transport.sniff", true).build();
        addClient(setting, ips);
    }

    /**
     * 判断索引是否存在
     *
     * @param client
     * @param index
     * @return
     */
    public static boolean indexExists(Client client, String index) {
        IndicesExistsRequest request = new IndicesExistsRequest(index);
        IndicesExistsResponse response = client.admin().indices().exists(request).actionGet();
        if (response.isExists()) {
            return true;
        }
        return false;
    }

    /**
     * 创建索引
     * @param client
     * @param index：索引名称
     * @param settings：索引配置
     * @param mapping：mapping
     * @return
     */
    public static boolean createIndex(Client client, String index, Settings.Builder settings, String type, String mapping){
        if (client==null || StringUtils.isEmpty(index) || StringUtils.isEmpty(mapping) || indexExists(client, index)){
            return false;
        }
        if (settings == null){
            settings = Settings.builder()
                    .put("index.number_of_shards", 5)
                    .put("index.number_of_replicas", 2);
        }
        CreateIndexResponse createIndexResponse = client.admin().indices()
                .prepareCreate(index)
                .setSettings(settings)
                .get();
        if (!createIndexResponse.isAcknowledged()){
            return false;
        }
        PutMappingResponse putMappingResponse = client.admin().indices().preparePutMapping(index).setType(type).setSource(mapping, XContentType.JSON).get();
        return putMappingResponse.isAcknowledged();
    }
}
