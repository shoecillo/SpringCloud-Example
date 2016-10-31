package com.sh.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RefreshScope
@Controller
public class FilesController 
{
	@Value("${rootPath}")
	private String rootPath;
	
	private StringBuffer buff;
	 
	
	@RequestMapping("/albumz")
	@ResponseBody
	public String getAlbums()
	{
		File dir = new File(rootPath);
		buff = new StringBuffer();
		getAllTree(dir);
		return buff.toString();
		
		
	}
	@RequestMapping("/albumz/{album}")
	@ResponseBody
	public String getAlbum(@PathVariable String album,HttpServletRequest request, HttpServletResponse response)
	{
		
		try {
			String url = request.getRequestURL().toString();
			url = java.net.URLDecoder.decode(url, "UTF-8");
			String [] urlParts = url.split("albumz\\/");
			String path = new String(Base64.getDecoder().decode(urlParts[1]));
			System.out.println(path);
			File dir = new File(path);
			buff = new StringBuffer();
			
			goUp(dir);
			getAllTree(dir);
			return buff.toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
		
	}
	
	
	private void goUp(File f)
	{
		
		System.out.println(f.getParentFile().getAbsolutePath().equals(new File(rootPath).getAbsolutePath()));
		boolean isRoot = f.getParentFile().getAbsolutePath().equals(new File(rootPath).getAbsolutePath());
		String href = "/albumz/";
		if(!isRoot)
		{
			String b64 = Base64.getEncoder().encodeToString(f.getParentFile().getAbsolutePath().getBytes());
			buff.append("<a href='"+href+b64+"'>");
			buff.append("...</a><br>");
		}
		else
		{
			buff.append("<a href='"+href+"'>");
			buff.append("...</a><br>");
		}
	}
	
	private void getAllTree(File dir)
	{
		File[] dirs = dir.listFiles();
		
		for(File f : dirs)
		{
			String href = "/albumz/";
			if(!f.isDirectory())
				href += "file/";
			
			String b64 = Base64.getEncoder().encodeToString(f.getAbsolutePath().getBytes());
			
			buff.append("<a href='"+href+b64+"'>");
			buff.append(f.getAbsolutePath()+"</a><br>");
			
				
		}
	}
	
	
	@RequestMapping(path="albumz/file/{name}",produces= MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public void foo(@PathVariable String name,HttpServletRequest request, HttpServletResponse response)
	{

		try 
		{
			String url = request.getRequestURL().toString();
			url = java.net.URLDecoder.decode(url, "UTF-8");
			String [] urlParts = url.split("albumz\\/file\\/");
			String path = new String(Base64.getDecoder().decode(urlParts[1]));
			File fil = new File(path);
			
			InputStream istr = new FileInputStream(fil);
			
			response.setContentType("audio/mpeg");
			response.addHeader("Content-Disposition", "attachment; filename="+fil.getName());
			
			IOUtils.copy(istr, response.getOutputStream());
			response.getOutputStream().flush();
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
