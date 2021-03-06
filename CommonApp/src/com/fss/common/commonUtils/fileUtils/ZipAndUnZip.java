package com.fss.common.commonUtils.fileUtils;

import com.fss.common.commonUtils.logUtils.Logs;

import java.io.*;
import java.util.zip.*;

public class ZipAndUnZip {
    /**
     * 解压缩文件到指定的目录.
     *
     * @param unZipfileName 需要解压缩的文件
     * @param mDestPath     解压缩后存放的路径
     */
    public static int unZip(String unZipfileName, String mDestPath) {
        if (!mDestPath.endsWith("/")) {
            mDestPath = mDestPath + "/";
        }
        FileOutputStream fileOut = null;
        ZipInputStream zipIn = null;
        ZipEntry zipEntry = null;
        File file = null;
        int readedBytes = 0;
        byte buf[] = new byte[4096];
        try {
            zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(unZipfileName)));
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                file = new File(mDestPath + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    // 如果指定文件的目录不存在,则创建之.
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    fileOut = new FileOutputStream(file);
                    while ((readedBytes = zipIn.read(buf)) > 0) {
                        fileOut.write(buf, 0, readedBytes);
                    }
                    fileOut.close();
                }
                zipIn.closeEntry();

            }
            return 1;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return 0;
        }
        catch (Exception e){
            e.printStackTrace();
            Logs.e(e,"");
            return 0;
        }
    }


    public static int unZip(InputStream unzipfileStream, String mDestPath) {
        if (!mDestPath.endsWith("/")) {
            mDestPath = mDestPath + "/";
        }
        FileOutputStream fileOut = null;
        ZipInputStream zipIn = null;
        ZipEntry zipEntry = null;
        File file = null;
        int readedBytes = 0;
        byte buf[] = new byte[4096];
        try {
            zipIn = new ZipInputStream(new BufferedInputStream(unzipfileStream));
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                Logs.d(zipEntry.getName());
                file = new File(mDestPath + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    // 如果指定文件的目录不存在,则创建之.
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    fileOut = new FileOutputStream(file);
                    while ((readedBytes = zipIn.read(buf)) > 0) {
                        fileOut.write(buf, 0, readedBytes);
                    }
                    fileOut.close();
                }
                zipIn.closeEntry();

            }
            return 1;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return 0;
        }
    }

    /**
     * Used to extract and return the extension of a given file.
     *
     * @param f Incoming file to get the extension of
     * @return <code>String</code> representing the extension of the incoming
     *         file.
     */
    public static String getExtension(String f) {
        String ext = "";
        int i = f.lastIndexOf('.');

        if (i > 0 && i < f.length() - 1) {
            ext = f.substring(i + 1);
        }
        return ext;
    }

    /**
     * Used to extract the filename without its extension.
     *
     * @param f Incoming file to get the filename
     * @return <code>String</code> representing the filename without its
     *         extension.
     */
    public static String getFileName(String f) {
        String fname = "";
        int i = f.lastIndexOf('.');

        if (i > 0 && i < f.length() - 1) {
            fname = f.substring(0, i);
        }
        return fname;
    }

    public static void gzips() throws Exception {

        //做准备压缩一个字符文件，注，这里的字符文件要是GBK编码方式的
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("d:/test/a/all.json"), "UTF-8"));

        BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("d:/test/a/test.txt.gz")));
        System.out.println("开始写压缩文件...");
        int c;
        while ((c = in.read()) != -1) {

            out.write(String.valueOf((char) c).getBytes("UTF-8"));
        }
        in.close();
        out.close();

    }

    public static void unzipgzips(String inFileName) throws Exception {
        try {

            if (!getExtension(inFileName).equalsIgnoreCase("gz")) {
                System.err.println("File name must have extension of \".gz\"");
                System.exit(1);
            }

            System.out.println("Opening the compressed file.");
            GZIPInputStream in = null;
            try {
                in = new GZIPInputStream(new FileInputStream(inFileName));
            } catch (FileNotFoundException e) {
                System.err.println("File not found. " + inFileName);
                System.exit(1);
            }

            System.out.println("Open the output file.");
            String outFileName = getFileName(inFileName);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(outFileName);
            } catch (FileNotFoundException e) {
                System.err.println("Could not write to file. " + outFileName);
                System.exit(1);
            }

            System.out.println("Transfering bytes from compressed file to the output file.");
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            System.out.println("Closing the file and stream");
            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    public static void zipFile(String fileFrom, String fileTo) {

        try {
            File file = new File(fileFrom);
            file.getName();
            FileInputStream in = new FileInputStream(fileFrom);
            FileOutputStream out = new FileOutputStream(fileTo);
            ZipOutputStream zipOut = new ZipOutputStream(out);
            ZipEntry entry = new ZipEntry(file.getName());
            zipOut.putNextEntry(entry);
            int nNumber;
            byte[] buffer = new byte[512];
            while ((nNumber = in.read(buffer)) != -1) {
                zipOut.write(buffer, 0, nNumber);

            }
            zipOut.close();

            out.close();
            in.close();
            System.out.print("success" + file.getName());
        } catch (IOException e) {

            System.out.println(e);
        }
    }
}

