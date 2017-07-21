/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package douyu;

import com.google.gson.*;
import java.io.*;
import java.net.*;
import org.apache.commons.collections4.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author yansunkang
 */
public class Douyu {

    private static HSSFWorkbook workbook = null;

    /**
     * @throws java.lang.Exception
     */
    public static void run() throws Exception {
        // TODO code application logic here
        String url = "http://open.douyucdn.cn/api/RoomApi/room/71415";

        Attribute attr = new Attribute();
        BufferedReader reader = null;
        BufferedWriter writer = null;

        String result = processJson(reader, writer, url, attr);
        writeIntoFile(attr, "/Users/yansunkang/Desktop/Douyu/Douyu_Data.xls", "Yin Zi");
    }

    public static String processJson(BufferedReader reader, BufferedWriter writer, String url, Attribute attr) {
        String result = "";
        try {

            FileWriter wr = new FileWriter("/Users/yansunkang/Desktop/Douyu/info.txt");
            writer = new BufferedWriter(wr);
            URL realURL = new URL(url);

            URLConnection connection = realURL.openConnection();

            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {

                result += line;

            }
        } catch (IOException e) {
            System.out.println("error founded!");
        } finally {

            try {
                if (reader != null && writer != null) {
                    //build Json Object to parse elements in Json String
                    JsonElement jelement = new JsonParser().parse(result);
                    JsonObject object = jelement.getAsJsonObject();
                    String error = object.get("error").toString();

                    //parse error
                    JsonObject data = object.getAsJsonObject("data");

                    //parse all the elements in data
                    String room_id = data.get("room_id").toString();
                    //parse room id
                    String cate_name = data.get("cate_name").toString();
                    //parse cate_name
                    String room_status = data.get("room_status").toString();
                    //parse room status
                    String online = data.get("online").toString();
                    //parse online number
                    String start_time = data.get("start_time").toString();

                    attr.setError(error);
                    attr.setRoomNum(room_id);
                    attr.setCateName(cate_name);
                    attr.setOnline(online);
                    attr.setStatus(room_status);
                    attr.setTime(start_time);
                    System.out.println("错误信息：" + error + "\n房间id：" + room_id
                            + "\n分类名称：" + cate_name + "\n房间状态：" + room_status + "\n在线人数：" + online + "\n开播时间：" + start_time);

                    ///+ "\n在线人数：" + online + "\n开播时间：" + start_time);
                    //writer.write("错误信息：" + error + "\n房间id：" + room_id + "\n分类名称：" + cate_name +"\n房间状态：" + room_status
                    ///+ "\n在线人数：" + online + "\n开播时间：" + start_time);
                    //writer.close();
                    //reader.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void writeIntoFile(Attribute attr, String fileName, String sheetName) throws Exception {
        if (!fileExist(fileName)) {

            String[] title = {"Error", "Room_num", "Cate_name", "Status", "Online", "Time"};
            createExcel(fileName, sheetName, title);
        } else {

            try {
                FileInputStream file = new FileInputStream(fileName);

                POIFSFileSystem fs = new POIFSFileSystem(file);
                workbook = new HSSFWorkbook(fs);
                writeToExcel(attr, fileName, sheetName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static boolean fileExist(String fileDir) {
        boolean flag = false;
        File file = new File(fileDir);
        flag = file.exists();
        return flag;
    }

    public static void createExcel(String fileDir, String sheetName, String[] titleRow) throws Exception {

        workbook = new HSSFWorkbook();

        HSSFSheet sheet1 = workbook.createSheet(sheetName);

        FileOutputStream out = null;
        try {

            HSSFRow row = workbook.getSheet(sheetName).createRow(0);
            for (short i = 0; i < titleRow.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(titleRow[i]);
            }
            out = new FileOutputStream(fileDir);
            workbook.write(out);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeToExcel(Attribute attr, String fileName, String sheetName) throws Exception {
        String error = attr.getError() + "";
        String room_num = attr.getRoomNum();
        String cate_name = attr.getCateName();
        String status = "no data";
        if (attr.getStatus()) {
            status = "true";
        } else {
            status = "false";
        }
        String online = attr.getOnline() + "";
        String time = attr.getTime();

        String[] datas = {error, room_num, cate_name, status, online, time};
        HSSFSheet sheet = workbook.getSheet(sheetName);
        HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
        FileOutputStream out = new FileOutputStream(fileName);
        for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
            row.createCell(i).setCellValue(datas[i]);

        }

        out.flush();
        workbook.write(out);
        out.close();

    }
}