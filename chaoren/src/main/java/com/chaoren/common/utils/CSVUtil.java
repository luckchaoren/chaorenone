package com.chaoren.common.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CSVUtil {
    public static <T> String exportCsv(String[] titles,String[] propertys,List<T> list,String filepath) throws IOException, IllegalArgumentException, IllegalAccessException{
        File file = new File(filepath);
        //构建输出流，同时指定编码
        OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file), "gbk");

        //csv文件是逗号分隔，除第一个外，每次写入一个单元格数据后需要输入逗号
        for(String title : titles){
            ow.write(title);
            ow.write(",");
        }
        //写完文件头后换行
        ow.write("\r\n");
        //写内容
        for(Object obj : list){
            //利用反射获取所有字段
            Field[] fields = obj.getClass().getDeclaredFields();
            for(String property : propertys){
                for(Field field : fields){
                    //设置字段可见性
                    field.setAccessible(true);
                    if(property.equals(field.getName())){
                        ow.write(field.get(obj).toString());
                        ow.write(",");
                        continue;
                    }
                }
            }
            //写完一行换行
            ow.write("\r\n");
        }
        ow.flush();
        ow.close();
        return "0";
    }

    public static <T> String exportCsvOneToOne(String[] titles,String[] titles1,String[] propertys,List<T> list,String filepath) throws IOException, IllegalArgumentException, IllegalAccessException,Exception{
        File file = new File(filepath+"_temp");
        //构建输出流，同时指定编码
        OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file), "utf-8");

        //csv文件是逗号分隔，除第一个外，每次写入一个单元格数据后需要输入逗号
        for(String title : titles){
            ow.write(title);
            ow.write(",");
        }

        ow.write("\r\n");
        for(String title : titles1){
            ow.write(title);
            ow.write(",");
        }
        //写完文件头后换行
        ow.write("\r\n");
        //写内容
        for(Object obj : list){
            //利用反射获取所有字段
            Field[] fields = obj.getClass().getDeclaredFields();
            for(String property : propertys){
                for(Field field : fields){
                    //设置字段可见性
                    field.setAccessible(true);
                    if(property.equals(field.getName())){
                        ow.write(field.get(obj)==null?"null":field.get(obj).toString());
                        ow.write(",");
                        continue;
                    }
                }
            }
            //写完一行换行
            ow.write("\r\n");
        }
        ow.flush();
        ow.close();
        String[][] str=getCsvData(filepath+"_temp");
        String[][] str1=switchRowAndCol(str);
        writeArraytoCSV(str1,filepath);
        deleteFile(filepath+"_temp");
        return "0";
    }

    public static String[][] getCsvData(String filePath)throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
        String line="";
        Integer column_num=0;
        ArrayList<String[]> lineList=new ArrayList<>();
        while ((line=br.readLine())!=null){
            StringTokenizer st = new StringTokenizer(line, ",");
            if (column_num==0){
                column_num=line.split("\\,").length;
            }
            String[] currCol = new String[column_num];
            for (int i=0;i<column_num;i++){
                if (st.hasMoreTokens()){
                    currCol[i]=st.nextToken();
                }
            }
            lineList.add(currCol);
        }
        String[][] str=new String[lineList.size()][column_num];
        for (int i=0;i<lineList.size();i++){
            for (int j=0;j<column_num;j++){
                str[i][j]=lineList.get(i)[j];
            }
        }
        br.close();
        return str;
    }
    public static String[][] switchRowAndCol(String[][] data){
        String[][] str = new String[data[0].length][data.length];
        for (int i=0;i<data[0].length;i++){
            for (int j=0;j<data.length;j++){
                str[i][j]=data[j][i];
            }
        }

        return str;
    }

    public static String writeArraytoCSV(String[][] data, String filePath)throws  Exception{
        File file = new File(filePath);
        //构建输出流，同时指定编码
        OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file), "gbk");
        for (int i=0;i<data.length;i++){
            for (int j=0;j<data[0].length;j++){
                ow.write(data[i][j]);
                ow.write(",");
            }
            ow.write("\r\n");
        }
        ow.flush();
        ow.close();
        return null;
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

}
