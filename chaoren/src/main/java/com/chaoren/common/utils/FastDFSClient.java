//package com.chaoren.common.utils;
//
//import com.sensetime.dm.config.FastDFSClientConfig;
//import com.sensetime.dm.exceptions.FileSystemException;
//import org.apache.commons.io.IOUtils;
//import org.csource.common.NameValuePair;
//import org.csource.fastdfs.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//
//public class FastDFSClient {
//
//	private static StorageClient1 storageClient1 = null;
//    private static Logger logger = LoggerFactory.getLogger(FastDFSClient.class);
//
//
//    // 初始化FastDFS Client
//    static {
//        try {
//            ClientGlobal.init(FastDFSClientConfig.getCONFIG_FILENAME());
//            TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
//            TrackerServer trackerServer = trackerClient.getConnection();
//            if (trackerServer == null) {
//                throw new IllegalStateException("getConnection return null");
//            }
//
//            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
//            if (storageServer == null) {
//                throw new IllegalStateException("getStoreStorage return null");
//            }
//
//            storageClient1 = new StorageClient1(trackerServer,storageServer);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 上传文件
//     * @param file 文件对象
//     * @param fileName 文件名
//     * @return
//     */
//    public static String uploadFile(File file, String fileName) {
//        return uploadFile(file,fileName,null);
//    }
//
//    /**
//     * 上传文件
//     * @param file 文件对象
//     * @param fileName 文件名
//     * @param metaList 文件元数据
//     * @return
//     */
//    public static String uploadFile(File file, String fileName, Map<String,String> metaList) {
//        try {
//            byte[] buff = IOUtils.toByteArray(new FileInputStream(file));
//            NameValuePair[] nameValuePairs = null;
//            if (metaList != null) {
//                nameValuePairs = new NameValuePair[metaList.size()];
//                int index = 0;
//                for (Iterator<Map.Entry<String,String>> iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
//                    Map.Entry<String,String> entry = iterator.next();
//                    String name = entry.getKey();
//                    String value = entry.getValue();
//                    nameValuePairs[index++] = new NameValuePair(name,value);
//                }
//            }
//            System.out.println(fileName);
//            String[] picName = fileName.split(""+File.separatorChar+".");
//            System.out.println(picName[1]);
//            return storageClient1.upload_file1(buff,""+picName[1],nameValuePairs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 上传文件到服务器
//     * @param image
//     * @param fileName
//     * @return
//     */
//    public static String uploadPictureFile(MultipartFile image, String fileName) {
//        try {
//            String[] picName = fileName.split(""+File.separatorChar+".");
//            String address = picName[1];
//            String uploadFile = storageClient1.upload_file1(image.getBytes(),address,(NameValuePair[])null);
//            if(uploadFile != null) {
//            	uploadFile = FastDFSClientConfig.getFastdfsUrlPrefix() + uploadFile;
//            }
//            return uploadFile;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 获取文件元数据
//     * @param fileId 文件ID
//     * @return
//     */
//    public static Map<String,String> getFileMetadata(String fileId) {
//        try {
//            NameValuePair[] metaList = storageClient1.get_metadata1(fileId);
//            if (metaList != null) {
//                HashMap<String,String> map = new HashMap<String, String>();
//                for (NameValuePair metaItem : metaList) {
//                    map.put(metaItem.getName(),metaItem.getValue());
//                }
//                return map;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 删除文件
//     * @param fileId 文件ID
//     * @return 删除失败返回-1，否则返回0
//     */
//    public static int deleteFile(String fileId) {
//        try {
//            return storageClient1.delete_file1(fileId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
//
//    /**
//     * 下载文件
//     * @param fileId 文件ID（上传文件成功后返回的ID）
//     * @param outFile 文件下载保存位置
//     * @return
//     */
//    public static int downloadFile(String fileId, File outFile) {
//        FileOutputStream fos = null;
//        try {
//            byte[] content = storageClient1.download_file1(fileId);
//            fos = new FileOutputStream(outFile);
//            //sIOUtil.copy(content,fos);
//            return 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return -1;
//    }
//
//    public static byte[] downloadObject(String fileName) throws FileSystemException {
//        byte[] filedata = null;
//        try {
//            String sGroup = fileName.substring(0, fileName.indexOf("/"));
//            String sName = fileName.substring(fileName.indexOf("/") + 1);
//            filedata = storageClient1.download_file(sGroup, sName);
//        } catch (Exception e) {
//            logger.error(">>>download file byte error.");
//            throw new FileSystemException(e);
//        }
//        return filedata;
//    }
//}
