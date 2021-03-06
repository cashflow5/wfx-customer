package com.yougou.wfx.customer.common.utils;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.net.URL;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.yougou.wfx.customer.model.commodity.CommodityStyleVo;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * @author: liu.d
 * @date: 2016-1-13 上午10:50:40
 * @version: 1.0
 */
public class MatrixToImageWriter {
	
	private final static Logger logger = LoggerFactory.getLogger(MatrixToImageWriter.class);

	private final static String rowText1="优购微零售";
	private final static String rowText2="分享商品, 赚取佣金";
	private final static String rowText3="微信公众号：优购微零售官方服务号";
	private final static String rowText4="账号：yougou_retail";
	/**
	 * 1、设置二维码矩阵，参数：编码、容错率、边框
	 * @param text
	 * @param width
	 * @param height
	 * @return
	 * @throws WriterException
	 */
	private static BitMatrix setBitMatrix(String text, int width, int height) throws WriterException {
		// 生成二维码中的设置
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 编码设置
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 容错率
		hints.put(EncodeHintType.MARGIN, 0); // 边框设置
		return new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
	}

	/**
	 * 2、将二维码矩阵转换化图片，并放入缓冲内。
	 * @param matrix
	 * @return
	 */
	private static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		return image;
	}

	/**
	 * 3、将logo绘制至二维码
	 * @param image
	 * @param logoPath 
	 * @throws IOException
	 */
	private static void toDrawLogo(BufferedImage image, String logoPath) throws IOException {
		Graphics2D g = image.createGraphics();
		// 读取logo图片
		URL logoUrl = new URL(logoPath);
		if (logoUrl==null) {
			logger.warn("The logoFile is not exist");
			return;
		}
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		BufferedImage logo = ImageIO.read(logoUrl);
		// 设置二维码大小，太大，会覆盖二维码，此处20%
		int logoWidth = logo.getWidth(null) > image.getWidth() * 16 / 100 ? (image.getWidth() * 16 / 100) : logo.getWidth(null);
		int logoHeight = logo.getHeight(null) > image.getHeight() * 16/ 100 ? (image.getHeight() * 16 / 100) : logo.getHeight(null);
		// 设置logo图片放置位置中心
		int x = (image.getWidth() - logoWidth) / 2;
		int y = (image.getHeight() - logoHeight) / 2;
		// 右下角，15为调整值
		// int x = image.getWidth() - logoWidth-15;
		// int y = image.getHeight() - logoHeight-15;
		// 开始合并绘制图片
		g.drawImage(logo, x, y, logoWidth, logoHeight, null);
		g.drawRoundRect(x, y, logoWidth, logoHeight, 15, 15);
		// logo边框大小
		g.setStroke(new BasicStroke(2));
		// logo边框颜色
		g.setColor(Color.WHITE);
		g.drawRect(x, y, logoWidth, logoHeight);
		g.dispose();
		logo.flush();
	}
	
	/**
	 * 3、将logo绘制至二维码
	 * @param image
	 * @param logoPath 
	 * @throws IOException
	 */
	private static void toDrawPic(BufferedImage image, String commPath) throws IOException {
		Graphics2D g = image.createGraphics();
		// 读取商品图片
		URL commUrl = new URL(commPath);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		BufferedImage commPic = ImageIO.read(commUrl);
		// 设置二维码大小
		int logoWidth = 40;
		int logoHeight = 40;
		// 设置logo图片放置位置中心
		int x = (image.getWidth() - logoWidth) / 2;
		int y = (image.getHeight() - logoHeight) / 2;
		// 开始合并绘制图片
		g.drawImage(commPic, x, y, logoWidth, logoHeight, null);
		g.drawRoundRect(x, y, logoWidth, logoHeight, 15, 15);
		// 图片边框大小
		g.setStroke(new BasicStroke(2));
		// 图片边框颜色
		g.setColor(Color.white);
		g.drawRect(x, y, logoWidth, logoHeight);
		g.dispose();
		commPic.flush();
	}

	/**
	 * @param text 二维码识别的数据
	 * @param width 二维码的宽度
	 * @param height 二维码的长度
	 * @param logoPath 二维码的logo图片文件绝对路径
	 * @param filePath 二维码生成的绝对路径
	 * @return
	 */
	public static boolean drawQrCodeToFile(String text, int width, int height, String logoPath, String codeFilePath) {
		try {
			if (StringUtils.isBlank(codeFilePath)) {
				logger.warn("codeFilePath is not null");
				return false;
			}
			File outFile = new File(codeFilePath);
			if (!outFile.exists() && !outFile.isDirectory()) {
				outFile.mkdirs();
			}
			// 1、设置二维码矩阵，参数：编码、容错率、边框
			BitMatrix bitMatrix = setBitMatrix(text, width, height);
			// 2、将二维码矩阵转换化图片，并放入缓冲内
			BufferedImage image = toBufferedImage(bitMatrix);
			if (image == null) {
				logger.warn("The BufferedImage is empty");
				return false;
			}
			// 3、将logo绘制至二维码
			if (StringUtils.isNotBlank(logoPath)) {
				try {
					toDrawLogo(image, logoPath);
				} catch (Exception e) {
					logger.error("to draw logo fail", e);
				}
			}
			image.flush();
			// 4、写入文件
			String format = "jpeg";
			if (!ImageIO.write(image, format, outFile)) {
				logger.warn("Could not write an image of format " + format + " to " + outFile);
				return false;
			}
		} catch (Exception e) {
			logger.error("write an image fail ", e);
		}
		return true;
	}

	/**
	 * @param text 二维码识别的数据
	 * @param width 二维码的宽度
	 * @param height 二维码的长度
	 * @param logoPath 二维码的logo图片文件绝对路径
	 * @param filePath 二维码输出流
	 * @return
	 */
	public static boolean drawQrCodeToStream(String text, int width, int height, String logoPath, OutputStream codeOutStream) {
		try {
			if (codeOutStream == null) {
				logger.warn("The codeOutStream is not exist");
				return false;
			}
			// 1、设置二维码矩阵，参数：编码、容错率、边框
			BitMatrix bitMatrix = setBitMatrix(text, width, height);
			// 2、将二维码矩阵转换化图片，并放入缓冲内
			BufferedImage image = toBufferedImage(bitMatrix);
			if (image == null) {
				logger.warn("The BufferedImage is empty");
				return false;
			}
			// 3、将logo绘制至二维码
			if (StringUtils.isNotBlank(logoPath)) {
				try {
					toDrawLogo(image, logoPath);
				} catch (Exception e) {
					logger.error("to draw logo fail", e);
				}
			}
			image.flush();
			// 4、写入流
			String format = "png";
			if (!ImageIO.write(image, format, codeOutStream)) {
				logger.warn("Could not write an image of format " + format + " to " + codeOutStream);
				return false;
			}
		} catch (Exception e) {
			logger.error("write an image fail ", e);
		} finally {
			try {
				codeOutStream.flush();
				codeOutStream.close();
			} catch (IOException e) {
				logger.error("write an image fail ", e);
			}
		}
		return true;
	}

	public static boolean drawWholeToStream ( ShopOutputDto shop, String text, int width, int height, OutputStream codeOutStream) {
		try {
			if (codeOutStream == null) {
				logger.warn("The codeOutStream is not exist");
				return false;
			}

			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(codeOutStream);
//			//画出画布
//			JPEGEncodeParam jpegEncodeParam = encoder
//			.getDefaultJPEGEncodeParam(image);
//			jpegEncodeParam.setQuality(1f, false);
//			jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
//			jpegEncodeParam.setXDensity(72);
//			jpegEncodeParam.setYDensity(72);
			
			// 1、设置二维码矩阵，参数：编码、容错率、边框
			BitMatrix bitMatrix = setBitMatrix(text, 300, 300);
			// 2、将二维码矩阵转换化图片，并放入缓冲内
			BufferedImage image2 = toBufferedImage(bitMatrix);
			if (image2 == null) {
				logger.warn("The BufferedImage is empty");
				return false;
			}
			
			try {
				// 3、将logo绘制至二维码  
				if (StringUtils.isNotBlank(shop.getLogoUrl())) {
					toDrawLogo(image2, shop.getLogoUrl());
				}
				//4、将二维码绘制至外部白板
				toDrawPanel(image,shop.getShopCode(),image2);
			} catch (Exception e) {
				logger.error("to draw logo fail", e);
			}
			
			image.flush();
			// 4、写入流
			String format = "png";
			if (!ImageIO.write(image, format, codeOutStream)) {
				logger.warn("Could not write an image of format " + format + " to " + codeOutStream);
				return false;
			}
		} catch (Exception e) {
			logger.error("write an image fail ", e);
		} finally {
			try {
				codeOutStream.flush();
				codeOutStream.close();
			} catch (IOException e) {
				logger.error("write an image fail ", e);
			}
		}
		return true;
		
	}
	
	public static boolean drawCommQrCode (CommodityStyleVo commVo,String text,String shopNo,int width,int height, OutputStream outStream) {
		try {
			if (outStream == null) {
				logger.warn("The codeOutStream is not exist");
				return false;
			}
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			
			// 1、设置二维码矩阵，参数：编码、容错率、边框
			BitMatrix bitMatrix = setBitMatrix(text, 153, 153);
			// 2、将二维码矩阵转换化图片，并放入缓冲内
			BufferedImage qrCodeImage = toBufferedImage(bitMatrix);
			if (qrCodeImage == null) {
				logger.warn("The BufferedImage is empty");
				return false;
			}
			
			try {
				// 3、将图片绘制至二维码  
				if (StringUtils.isNotBlank(commVo.getDefaultPic())) {
					toDrawPic(qrCodeImage, commVo.getDefaultPic());
				}
				//4、将二维码绘制至外部白板
				drawAllEle(image,commVo,qrCodeImage,commVo.getDefaultPic(),shopNo);
			} catch (Exception e) {
				logger.error("to draw logo fail", e);
			}
			
			image.flush();
			// 4、写入流
			String format = "png";
			if (!ImageIO.write(image, format, outStream)) {
				logger.warn("将png格式的图片写入输出流出错。");
				return false;
			}
		} catch (Exception e) {
			logger.error("write an image fail ", e);
		} finally {
			try {
				outStream.flush();
				outStream.close();
			} catch (IOException e) {
				logger.error("write an image fail ", e);
			}
		}
		return true;
	}
	
	/**
	 * 3、将logo绘制至二维码
	 * @param image
	 * @param logoPath static\images\erweima\sharePNG.png
	 * @throws IOException
	 */
	private static void toDrawPanel(BufferedImage image, String shopCode,BufferedImage erweimaImage) throws IOException {
		Graphics2D g = image.createGraphics();
		//消除锯齿
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// 设置二维码大小，太大，会覆盖二维码，此处20%
		int logoWidth = erweimaImage.getWidth(null) ;
		int logoHeight = erweimaImage.getHeight(null); 
		// 设置logo图片放置位置中心
		int x = (image.getWidth() - logoWidth) / 2;
		int y = (image.getHeight() - logoHeight) / 2;
		g.setBackground( Color.white);
		g.clearRect(0, 0, image.getWidth(), image.getHeight());//通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
		// 文字头部
		g.setColor(Color.black);
		
		g.setFont(new Font("microsoft yahei", Font.TRUETYPE_FONT, 50));
		g.drawString(rowText1, 120,100);
		
		g.setFont(new Font("microsoft yahei",Font.TRUETYPE_FONT,30));
		g.drawString(rowText2, 120, 145);
		g.setFont(new Font("microsoft yahei",Font.TRUETYPE_FONT,24));
		// 店铺编码
		g.drawString(shopCode, 179, 216);
		// 文字底部
		g.setFont(new Font("microsoft yahei",Font.TRUETYPE_FONT,18));
		g.drawString(rowText3, 100, 660);
//
		g.drawString(rowText4, 100, 700);
		// 开始合并绘制图片
		g.drawImage(erweimaImage, x, y, logoWidth, logoHeight, null);
		g.drawRoundRect(x, y, logoWidth, logoHeight, 15, 15);
		// logo边框大小
		g.setStroke(new BasicStroke(2));
		// logo边框颜色
		g.setColor(Color.WHITE);
		g.drawRect(x, y, logoWidth, logoHeight);
		g.dispose();
		erweimaImage.flush();
	}
	
	private static void drawAllEle(BufferedImage image,CommodityStyleVo commVo,BufferedImage qrcodeImage,String commPic,String shopNo) throws IOException {
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		int qrWidth = qrcodeImage.getWidth() ;
		int qrHeight = qrcodeImage.getHeight();
		// 
		int x = qrHeight + 30;
		int y = 25;
		g.setBackground(Color.white);
		//通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
		g.clearRect(0, 0, image.getWidth(), image.getHeight());
		//绘制店铺编号
		g.setColor(Color.black);
		g.setFont(new Font("microsoft yahei", Font.TRUETYPE_FONT, 12));
		int strWidth = g.getFontMetrics().stringWidth(shopNo);
		g.drawString(shopNo, image.getWidth()/2 - strWidth/2, 13);
		//绘制商品图片
		if(null != commPic){
			URL commUrl = new URL(commVo.getDefaultPic());
			BufferedImage commImage = ImageIO.read(commUrl);
			g.drawImage(commImage, 10, y, qrWidth, qrHeight, null);
		}
		// 商品名称
		g.setColor(Color.black);
		g.setFont(new Font("microsoft yahei", Font.TRUETYPE_FONT, 14));
		String commName = commVo.getCommodityName();
		if(StringUtils.isNotBlank(commName)){
			int textLength = 11;
			if(commName.length()<=textLength){
				g.drawString(commName, 10,qrHeight+y+20);
			}else if(commName.length()<=textLength*2){
				g.drawString(commName.substring(0,textLength), 10,qrHeight+y+20);
				g.drawString(commName.substring(textLength), 10,qrHeight+y+42);
			}else{
				g.drawString(commName.substring(0,textLength), 10,qrHeight+y+20);
				g.drawString(commName.substring(textLength,textLength*2), 10,qrHeight+y+42);
			}
		}
		
		// 商品价格
		DecimalFormat priceDf = new DecimalFormat("##0.00");
		g.setColor(Color.red);
		g.setFont(new Font("microsoft yahei", Font.TRUETYPE_FONT, 13));
		g.drawString("￥"+priceDf.format(commVo.getWfxPrice()), 10,qrHeight+y+62);
		g.setColor(Color.black);
		g.setFont(new Font("microsoft yahei",Font.TRUETYPE_FONT,14));
		g.drawString("长按或扫描二维码", qrWidth+30, qrHeight+y+20);
		g.drawString("查看详情", qrWidth+30, qrHeight+y+42);
		
		// 开始合并绘制图片
		g.drawImage(qrcodeImage, x, y, qrWidth, qrHeight, null);
		g.drawRoundRect(x, y, qrWidth, qrHeight, 2, 2);
		// 二维码边框大小
		g.setStroke(new BasicStroke(1));
		// 二维码边框颜色
		g.setColor(Color.gray);
		//g.drawRect(x-5, y-5, qrWidth+10, qrHeight+10);
		g.dispose();
		qrcodeImage.flush();
	}
	
	public static void main(String[] args) {
		//drawQrCodeToFile("weixin://wxpay/bizpayurl?pr=nx0lgL9", 235, 235, "", "d:\\weixinCode\\" + File.separator + "scanCode.gif");
		DecimalFormat priceDf = new DecimalFormat("##0.00");
		System.out.println(priceDf.format(1002120313.123));
	}
}