package com.bj58.zhaoren.crawl.spider;

import java.util.Date;

import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bj58.zhaoren.crawl.HttpclientGethtmlStr.GethtmlStrByUrl;
import com.bj58.zhaoren.crawl.entity.Tboqii;
import com.bj58.zhaoren.crawl.service.TboqiiService;
import com.bj58.zhaoren.crawl.util.StingUtils;


/**
 * @author fjsh123
 * 波奇服务商店抓取
 */
public class BoqiiCrawler {
	 int currentnum=0;//起始值
	 int maxnum=Integer.MAX_VALUE;//最后结束的页数，后期从配置文件读取
	// String basicUrl="http://vet.boqii.com/bj/list-0-0-0-";//北京读取，这个公司就没几个地区的，同时格式一样，只是城市名称是用字母代替的
	 String basicUrl="http://vet.boqii.com/sh/list-0-0-0-";//北京读取，这个公司就没几个地区的，同时格式一样，只是城市名称是用字母代替的
	 String requeString=basicUrl+0;//拼接请求的urllist
	 GethtmlStrByUrl ghsu=new GethtmlStrByUrl();
	 static TboqiiService tboqiiService;
	 static
	 {
		 ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
		tboqiiService=(TboqiiService) ac.getBean("tboqiiService");
	 }
	public void crawl()
	{
		
		while(currentnum<maxnum)
		{			
			String[] strings=ghsu.GetHtmlStr(requeString);
			Parser parser=Parser.createParser(strings[0], strings[1]);	
			try {
				NodeList nodedivlist=parser.extractAllNodesThatMatch(new TagNameFilter("div")).extractAllNodesThatMatch(new HasAttributeFilter("class","ui-list-item"));
				for(int i=0;i<nodedivlist.size();i++)
				{
					Parser parserShop=parser.createParser(nodedivlist.elementAt(i).toHtml(), strings[1]);
					NodeList nodea=parserShop.extractAllNodesThatMatch(new TagNameFilter("a"));
					LinkTag lt=(LinkTag) nodea.elementAt(0);
					//抽取商店页面中的详细信息
					this.extract(lt.getLink().trim());
				}
				Parser parsernext=Parser.createParser(strings[0], strings[1]);	
				NodeList nodenexta=parsernext.extractAllNodesThatMatch(new TagNameFilter("a"));
				NodeList nodenext=nodenexta.extractAllNodesThatMatch(new HasAttributeFilter("class", "ui-page-button"));
				LinkTag ltnext=(LinkTag) nodenext.elementAt(nodenext.size()-1);
				if(ltnext.getLinkText().equals("下一页"))
				{
					requeString=ltnext.getLink().trim();
				}
				else {
					break;
				}
//				System.out.println(lt.getText());
//				System.out.println(lt.getLink().trim());
//				System.out.println(lt.getLinkText());
			
			} catch (ParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		System.out.println(basicUrl+"数据抓取结束……");
	}
	/**
	 * 根据传入的商店页面详细信息抽取相应的信息并将此信息写入数据库相应的表中
	 * @param ListUrl
	 */
	private void extract(String shopurl)
	{
		String[] strings=ghsu.GetHtmlStr(shopurl);
		Parser parser=Parser.createParser(strings[0], strings[1]);	
		 Tboqii tboqii=new Tboqii();
		 tboqii.setCreateTime(new Date());
		 tboqii.setUpdateTime(new Date());
		 tboqii.setUrl(shopurl);
		 try {
			 //公司shop目录地址
			NodeList nodedivlist=parser.extractAllNodesThatMatch(new TagNameFilter("div")).extractAllNodesThatMatch(new HasAttributeFilter("class","location clearfix"));			
			tboqii.setCatalog(nodedivlist.elementAt(0).toPlainTextString().replaceAll("&gt;", ">"));
			
			parser=Parser.createParser(strings[0], strings[1]);	
			NodeList nodedivitem=parser.extractAllNodesThatMatch(new TagNameFilter("div")).extractAllNodesThatMatch(new HasAttributeFilter("class","shop-description"));
			Parser parseritem=Parser.createParser(nodedivitem.elementAt(0).toHtml(), strings[1]);
			//商店名称
			nodedivlist=parseritem.extractAllNodesThatMatch(new TagNameFilter("h1")).extractAllNodesThatMatch(new HasAttributeFilter("class", "shop-des-name"));
			tboqii.setCompanyName(nodedivlist.elementAt(0).toPlainTextString());
			//商店地址
			parseritem=Parser.createParser(nodedivitem.elementAt(0).toHtml(), strings[1]);
			nodedivlist=parseritem.extractAllNodesThatMatch(new TagNameFilter("div")).extractAllNodesThatMatch(new HasAttributeFilter("class", "shop-des-inner"));
			tboqii.setCompanyAddr(nodedivlist.elementAt(0).toPlainTextString().replaceAll("查看地图 / 周边街景", ""));
			//商店电话号码
			parseritem=Parser.createParser(nodedivitem.elementAt(0).toHtml(), strings[1]);
			nodedivlist=parseritem.extractAllNodesThatMatch(new TagNameFilter("div")).extractAllNodesThatMatch(new HasAttributeFilter("class","clearfix"));	
			tboqii.setMobile(StingUtils.extractMobile(nodedivlist.elementAt(2).toPlainTextString()));
			//商店其他信息
			parseritem=Parser.createParser(nodedivitem.elementAt(0).toHtml(), strings[1]);
			nodedivlist=parseritem.extractAllNodesThatMatch(new TagNameFilter("div")).extractAllNodesThatMatch(new HasAttributeFilter("class", "shop-des-assess"));
			tboqii.setOtherInfo(nodedivlist.elementAt(0).toPlainTextString());
			tboqiiService.insert(tboqii);
			
		 } catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			 
	}
}
