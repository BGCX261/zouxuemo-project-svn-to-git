/**
 * 
 */
package com.lily.dap.service.report2.impl.word;

import java.awt.Color;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.lowagie.text.Element;
import com.lowagie.text.Font;

/**
 * @author xuemozou
 *
 * 存放Word支持的Style风格
 */
public class WordCss implements Cloneable {
	private String fontName = "宋体";
	
	private short size = 9;
	
	private int style = Font.NORMAL;
	
	private short italic = Font.NORMAL;
	
	private Color color = Color.BLACK;
	
	private boolean strikeout = false;
	
	private byte underline = Font.UNDERLINE;
	
	private short borderWidthTop = 0;
	
	private short borderWidthBottom = 0;
	
	private short borderWidthLeft = 0;
	
	private short borderWidthRight = 0;
	
	private Color borderColorTop = Color.BLACK;
	
	private Color borderColorBottom = Color.BLACK;
	
	private Color borderColorLeft = Color.BLACK;
	
	private Color borderColorRight = Color.BLACK;
	
	private Color backgroundColor = Color.WHITE;
	
	private int horizontalHorizontalAlignment = Element.ALIGN_CENTER;
	
	private int verticalHorizontalAlignment = Element.ALIGN_MIDDLE;
	
	private short indention = 0;
	
	private boolean wrapText = true;

	public WordCss() {
	}

	public WordCss(String fontName, short size, short style,
			short italic) {
		this.fontName = fontName;
		this.size = size;
		this.style = style;
		this.italic = italic;
	}

	public WordCss(String fontName, short size, short style,
			short italic, boolean strikeout, byte underline, Color color,
			Color backgroundColor) {
		this.fontName = fontName;
		this.size = size;
		this.style = style;
		this.italic = italic;
		this.color = color;
		this.strikeout = strikeout;
		this.underline = underline;
		this.backgroundColor = backgroundColor;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public short getSize() {
		return size;
	}

	public void setSize(short size) {
		this.size = size;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public short isItalic() {
		return italic;
	}

	public void setItalic(short italic) {
		this.italic = italic;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isStrikeout() {
		return strikeout;
	}

	public void setStrikeout(boolean strikeout) {
		this.strikeout = strikeout;
	}

	public byte getUnderline() {
		return underline;
	}

	public void setUnderline(byte underline) {
		this.underline = underline;
	}

	public short getBorderWidthTop() {
		return borderWidthTop;
	}

	public void setBorderWidthTop(short borderWidthTop) {
		this.borderWidthTop = borderWidthTop;
	}

	public short getBorderWidthBottom() {
		return borderWidthBottom;
	}

	public void setBorderWidthBottom(short borderWidthBottom) {
		this.borderWidthBottom = borderWidthBottom;
	}

	public short getBorderWidthLeft() {
		return borderWidthLeft;
	}

	public void setBorderWidthLeft(short borderWidthLeft) {
		this.borderWidthLeft = borderWidthLeft;
	}

	public short getBorderWidthRight() {
		return borderWidthRight;
	}

	public void setBorderWidthRight(short borderWidthRight) {
		this.borderWidthRight = borderWidthRight;
	}

	public Color getBorderColorTop() {
		return borderColorTop;
	}

	public void setBorderColorTop(Color borderColorTop) {
		this.borderColorTop = borderColorTop;
	}

	public Color getBorderColorBottom() {
		return borderColorBottom;
	}

	public void setBorderColorBottom(Color borderColorBottom) {
		this.borderColorBottom = borderColorBottom;
	}

	public Color getBorderColorLeft() {
		return borderColorLeft;
	}

	public void setBorderColorLeft(Color borderColorLeft) {
		this.borderColorLeft = borderColorLeft;
	}

	public Color getBorderColorRight() {
		return borderColorRight;
	}

	public void setBorderColorRight(Color borderColorRight) {
		this.borderColorRight = borderColorRight;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getHorizontalHorizontalAlignment() {
		return horizontalHorizontalAlignment;
	}

	public void setHorizontalHorizontalAlignment(int horizontalHorizontalAlignment) {
		this.horizontalHorizontalAlignment = horizontalHorizontalAlignment;
	}

	public int getVerticalHorizontalAlignment() {
		return verticalHorizontalAlignment;
	}

	public void setVerticalHorizontalAlignment(int verticalHorizontalAlignment) {
		this.verticalHorizontalAlignment = verticalHorizontalAlignment;
	}

	public short getIndention() {
		return indention;
	}

	public void setIndention(short indention) {
		this.indention = indention;
	}

	public boolean isWrapText() {
		return wrapText;
	}

	public void setWrapText(boolean wrapText) {
		this.wrapText = wrapText;
	}

	public WordCss clone() throws CloneNotSupportedException {
		return (WordCss)super.clone();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WordCss)) {
			return false;
		}
		WordCss rhs = (WordCss) object;
		return new EqualsBuilder().appendSuper(super.equals(object)).append(
				this.borderColorLeft, rhs.borderColorLeft).append(
				this.borderWidthRight, rhs.borderWidthRight).append(this.strikeout,
				rhs.strikeout).append(this.verticalHorizontalAlignment,
				rhs.verticalHorizontalAlignment).append(this.style, rhs.style)
				.append(this.borderColorBottom, rhs.borderColorBottom).append(
						this.horizontalHorizontalAlignment, rhs.horizontalHorizontalAlignment).append(this.borderWidthLeft,
						rhs.borderWidthLeft).append(this.borderWidthTop, rhs.borderWidthTop)
				.append(this.color, rhs.color).append(this.borderColorRight,
						rhs.borderColorRight).append(this.size,
						rhs.size).append(this.italic, rhs.italic)
				.append(this.borderWidthBottom, rhs.borderWidthBottom).append(
						this.underline, rhs.underline).append(
						this.borderColorTop, rhs.borderColorTop).append(
						this.backgroundColor, rhs.backgroundColor).append(
						this.indention, rhs.indention).append(
						this.wrapText, rhs.wrapText).append(this.fontName,
						rhs.fontName).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(1357361335, 1045380061).appendSuper(
				super.hashCode()).append(this.borderColorLeft).append(
				this.borderWidthRight).append(this.strikeout).append(
				this.verticalHorizontalAlignment).append(this.style).append(
				this.borderColorBottom).append(this.horizontalHorizontalAlignment).append(
				this.borderWidthLeft).append(this.borderWidthTop).append(this.color)
				.append(this.borderColorRight).append(this.size)
				.append(this.italic).append(this.borderWidthBottom).append(
						this.underline).append(this.borderColorTop).append(
						this.backgroundColor).append(this.wrapText).append(
						this.fontName).append(this.indention).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("borderWidthRight", this.borderWidthRight).append(
						"size", this.size).append(
						"borderColorTop", this.borderColorTop).append(
						"horizontalHorizontalAlignment", this.horizontalHorizontalAlignment).append("borderWidthBottom",
						this.borderWidthBottom).append("borderColorRight",
						this.borderColorRight).append("strikeout",
						this.strikeout).append("borderColorBottom",
						this.borderColorBottom).append("borderWidthLeft",
						this.borderWidthLeft).append("backgroundColor",
						this.backgroundColor).append("fontName", this.fontName)
				.append("style", this.style).append("borderWidthTop",
						this.borderWidthTop).append("borderColorLeft",
						this.borderColorLeft).append("italic", this.italic)
				.append("verticalHorizontalAlignment", this.verticalHorizontalAlignment).append(
						"underline", this.underline)
				.append("color", this.color).append("wrapText", this.wrapText)
				.append("indention", this.indention).toString();
	}

}
