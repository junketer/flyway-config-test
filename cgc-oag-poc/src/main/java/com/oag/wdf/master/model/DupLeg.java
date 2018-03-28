package com.oag.wdf.master.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DL")
public class DupLeg implements Comparable<DupLeg> {

	String carrierCd;
	String serviceNum;

	public DupLeg() {
	}

	public DupLeg(String carrierCd, String serviceNum) {
		this.carrierCd = carrierCd;
		this.serviceNum = serviceNum;
	}

	public String getCarrierCd() {
		return carrierCd;
	}

	@XmlElement(name = "CCd")
	public void setCarrierCd(String carrierCd) {
		this.carrierCd = carrierCd;
	}

	public String getServiceNum() {
		return serviceNum;
	}

	@XmlElement(name = "SNum")
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carrierCd == null) ? 0 : carrierCd.hashCode());
		result = prime * result + ((serviceNum == null) ? 0 : serviceNum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		DupLeg other = (DupLeg) obj;

		if (carrierCd == null) {
			if (other.carrierCd != null)
				return false;
		} else if (!carrierCd.equals(other.carrierCd))
			return false;

		if (serviceNum == null) {
			if (other.serviceNum != null)
				return false;
		} else if (!serviceNum.equals(other.serviceNum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DupLeg [carrierCd=" + carrierCd + ", serviceNum=" + serviceNum + "]";
	}

	@Override
	public int compareTo(DupLeg o) {

		if (this.carrierCd.equals(o.carrierCd)) {

			return this.serviceNum.compareTo(o.serviceNum);

		} else {

			return this.carrierCd.compareTo(o.carrierCd);
		}

	}

}
