package com.cisco.pptx_to_jpg_converter.xslfchart.color;

/**
 * reference version 1 Part 4, 5.1.2.2.29
 * 
 * <simpleType name="ST_SchemeColorVal"> <restriction base="xsd:token">
 * <enumeration value="bg1"/> <enumeration value="tx1"/>
 * <enumeration value="bg2"/> <enumeration value="tx2"/>
 * <enumeration value="accent1"/> <enumeration value="accent2"/>
 * <enumeration value="accent3"/> <enumeration value="accent4"/>
 * <enumeration value="accent5"/> <enumeration value="accent6"/>
 * <enumeration value="hlink"/> <enumeration value="folHlink"/>
 * <enumeration value="phClr"/> <enumeration value="dk1"/>
 * <enumeration value="lt1"/> <enumeration value="dk2"/>
 * <enumeration value="lt2"/> </restriction> </simpleType>
 **/

public class SchemeColor {

	String val;
	int lumMod; // luminance modulation
	int lumOff;// luminance offset

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public int getLumMod() {
		return lumMod;
	}

	public void setLumMod(int lumMod) {
		this.lumMod = lumMod;
	}

	public int getLumOff() {
		return lumOff;
	}

	public void setLumOff(int lumOff) {
		this.lumOff = lumOff;
	}

}
