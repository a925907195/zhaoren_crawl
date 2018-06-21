package com.fjsh.expression.utils;

import com.fjsh.expression.constant.ManageConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @Author: <fujiansheng@meituan.com>
 * @Description：解压与压缩功能
 * @Date: Created in :2/4/2018 5:37 PM
 * @Modified by:
 */
public class ZipUtils {
    private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP 方法1
     *
     * @param srcDir           压缩文件夹路径
     * @param outPath          压缩文件输出流
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static int toZip(String srcDir, String outPath, boolean KeepDirStructure) {
        ZipOutputStream zos = null;
        try {
            FileOutputStream out = new FileOutputStream(new File(outPath));
            long start = System.currentTimeMillis();
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
//            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
            return ManageConstant.ReturnStatus.SUCCESS_ONE.getValue();
        } catch (Exception e) {
            logger.error("zip filepath error", e);
            return ManageConstant.ReturnStatus.FAIL_ZERO.getValue();

        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    logger.error("zip filepath error", e);
                }
            }
        }

    }

    /**
     * 压缩成ZIP 方法2
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out      压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles, OutputStream out) throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
//            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }

            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }

                }
            }
        }
    }

    /**
     * @param zipPath    zip路径
     * @param charset    编码
     * @param outPutPath 输出路径
     * @Description (解压)
     */
    private static int decompression(String zipPath, String charset, String outPutPath) {
        long startTime = System.currentTimeMillis();
        try {
            ZipInputStream Zin = new ZipInputStream(new FileInputStream(zipPath), Charset.forName(charset));//输入源zip路径
            BufferedInputStream Bin = new BufferedInputStream(Zin);
            String Parent = outPutPath; //输出路径（文件夹目录）
            File Fout = null;
            ZipEntry entry;
            try {
                while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
                    Fout = new File(Parent, entry.getName());
                    if (!Fout.exists()) {
                        (new File(Fout.getParent())).mkdirs();
                    }
                    FileOutputStream out = new FileOutputStream(Fout);
                    BufferedOutputStream Bout = new BufferedOutputStream(out);
                    int b;
                    while ((b = Bin.read()) != -1) {
                        Bout.write(b);
                    }
                    Bout.close();
                    out.close();
                    System.out.println(Fout + "解压成功");
                }
                Bin.close();
                Zin.close();
                return ManageConstant.ReturnStatus.SUCCESS_ONE.getValue();
            } catch (IOException e) {
                // TODO Auto-generated catch block
               logger.error("decompression error ",e);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logger.error("FileNotFoundException error ",e);
        }
        return ManageConstant.ReturnStatus.FAIL_ZERO.getValue();
    }

    public static void main(String[] args) throws Exception {
        /** 测试压缩方法1  */

//          ZipUtils.toZip("/Users/jianshengfu/temp/modeltrainingzip", "/Users/jianshengfu/temp/mytest01.zip", true);
          ZipUtils.toZip("/Users/jianshengfu/IdeaProjects/poi_search_rerank/src/main/resources/online_learning/ftrl/ftrl_online_20180118", "/Users/jianshengfu/temp/mytest02.zip", true);
       // ZipUtils.decompression("/Users/jianshengfu/temp/modelmanage_fjsh_32b5bb53-dd49-470d-8a0d-ae958c42f8dd_04f9061a-89f1-4677-b81a-24ae4ea35cbc.zip", "UTF-8", "/Users/jianshengfu/temp/");


    }
}