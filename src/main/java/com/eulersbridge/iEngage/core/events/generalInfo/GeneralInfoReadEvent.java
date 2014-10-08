package com.eulersbridge.iEngage.core.events.generalInfo;

/**
 * @author Yikai Gong
 */

public class GeneralInfoReadEvent 
{
	GeneralInfoDetails dets;

    public GeneralInfoReadEvent(GeneralInfoDetails dets){
        this.dets = dets;
    }

	/**
	 * @return the dets
	 */
	public GeneralInfoDetails getDets() {
		return dets;
	}

	/**
	 * @param dets the dets to set
	 */
	public void setDets(GeneralInfoDetails dets) {
		this.dets = dets;
	}
}
