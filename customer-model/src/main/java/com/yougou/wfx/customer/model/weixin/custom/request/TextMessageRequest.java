package com.yougou.wfx.customer.model.weixin.custom.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>Title: TextMessageRequest</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年6月30日
 */
public class TextMessageRequest extends MessageRequest{
	@JsonProperty("text")
	private Text text;
	
	public TextMessageRequest(){
		this.setMsgType("text");
		this.text = new Text();
	}
	
	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
	public class Text{
		@JsonProperty("content")
		private String content;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}
}
