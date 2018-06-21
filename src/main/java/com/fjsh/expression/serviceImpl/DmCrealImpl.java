package com.fjsh.expression.serviceImpl;

import com.fjsh.expression.constant.ManageConstant;
import com.fjsh.expression.utils.DownloadPicFromURL;
import com.fjsh.expression.utils.StringUtils;
import com.fjsh.expression.HttpclientGethtmlStr.GethtmlStrByUrl;
import com.fjsh.expression.service.Icrawl;
import com.fjsh.expression.utils.FileUtil;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * @Author: <fujiansheng@meituan.com>
 * @Description：
 * @Date: Created in :9/6/2018 1:45 PM
 * @Modified by:
 */
public class DmCrealImpl implements Icrawl {
    int currentnum = 1;//起始值
    int maxnum = 2;//最后结束的页数，后期从配置文件读取
    String domainUrl ;
    String basicUrl ;
    String requeString = basicUrl.replaceAll("num", "");//拼接请求的urllist
    GethtmlStrByUrl ghsu = new GethtmlStrByUrl();

    @Override
    public void crawl() {
        while (currentnum <= maxnum) {
            String[] strings=null;
            Parser parser=null;
            int retry=1;
            Boolean success=false;
            while (retry<3&&(!success)){
                try {
                    strings= ghsu.GetHtmlStr(requeString);
                    parser = Parser.createParser(strings[0], strings[1]);
                    success=true;
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                retry++;
            }
            try {
                NodeList nodedivlist = parser.extractAllNodesThatMatch(new TagNameFilter("div")).extractAllNodesThatMatch(new HasAttributeFilter("class", "box list channel"));
                Parser parserShop = parser.createParser(nodedivlist.elementAt(0).toHtml(), strings[1]);
                NodeList nodea = parserShop.extractAllNodesThatMatch(new TagNameFilter("a"));
                for (int j = 0; j < nodea.size(); j++) {
                    LinkTag lt = (LinkTag) nodea.elementAt(j);
                    String link = lt.getLink();
                    link=domainUrl+link;
                    String linkedText = lt.getLinkText();
                    linkedText = StringUtils.deleteTime(linkedText);
                    if (linkedText.equalsIgnoreCase("下一頁")) {
                        requeString = domainUrl + link;
                        break;
                    }
                    this.extract(link,linkedText);
                }
            } catch (ParserException e) {
                e.printStackTrace();
            } finally {
            }


        }
        System.out.println(basicUrl + "数据抓取结束……");
    }

    /**
     * 根据传入的页面详细信息抽取相应的信息并将此信息写入数据库相应的表中
     */
    private void extract(String linkUrl,String linkText) {
        String[] strings=null;
        Parser parser=null;
        int retry=1;
        Boolean success=false;
        while (retry<3&&(!success)){
            try {
                strings = ghsu.GetHtmlStr(linkUrl);
                parser = Parser.createParser(strings[0], strings[1]);
                success=true;
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry++;
        }

        try {
            //公司shop目录地址
            NodeList nodedivlist = parser.extractAllNodesThatMatch(new TagNameFilter("div")).extractAllNodesThatMatch(new HasAttributeFilter("class", "content"));
            Parser parserShop = parser.createParser(nodedivlist.elementAt(0).toHtml(), strings[1]);
            NodeList nodea = parserShop.extractAllNodesThatMatch(new TagNameFilter("img"));
            for (int j = 0; j < nodea.size(); j++) {
                ImageTag lt = (ImageTag) nodea.elementAt(j);
                String link = lt.getImageURL();
                String originFileName = link.substring(link.lastIndexOf("/") + 1, link.length());
                String dirPath= ManageConstant.basicPath+"/dmkt/"+linkText;
                FileUtil.createMultiDirs(dirPath);
                DownloadPicFromURL.downloadPicture(link,dirPath+"/"+originFileName);
            }

        } catch (ParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
