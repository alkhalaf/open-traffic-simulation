package com.opentrafficsimulation.screen.report;

import java.util.List;
import javax.swing.table.*;

public class TripTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 11232312312L;
	private List<TripInfo> list;
	
	public TripTableModel(List<TripInfo> list) {
		this.list = list;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		TripInfo tripInfo = list.get(arg0);
		switch(arg1) {
			case 0: return tripInfo.id;
			case 1: return tripInfo.depart;
			case 2: return tripInfo.departLane;
			case 3: return tripInfo.departPos;
			case 4: return tripInfo.departSpeed;
			case 5: return tripInfo.departDelay;
			case 6: return tripInfo.arrival;
			case 7: return tripInfo.arrivalLane;
			case 8: return tripInfo.arrivalPos;
			case 9: return tripInfo.arrivalSpeed;
			case 10: return tripInfo.duration;
			case 11: return tripInfo.routeLength;
			case 12: return tripInfo.waitSteps;
			case 13: return tripInfo.rerouteNo;
			case 14: return tripInfo.devices;
			case 15: return tripInfo.vType;
			case 16: return tripInfo.vaporized;
		}
		return null;
	}
	
	public void setList(List<TripInfo> list) {
		this.list = list;
	}


}
