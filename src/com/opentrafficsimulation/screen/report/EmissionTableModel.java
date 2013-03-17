package com.opentrafficsimulation.screen.report;

import java.util.List;
import javax.swing.table.*;

public class EmissionTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 11232312312L;
	private List<EmissionInfo> list;
	
	public EmissionTableModel(List<EmissionInfo> list) {
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
		EmissionInfo emissionInfo = list.get(arg0);
		switch(arg1) {
			case 0: return emissionInfo.id;
			case 1: return emissionInfo.eclass;
			case 2: return emissionInfo.co2;
			case 3: return emissionInfo.co;
			case 4: return emissionInfo.hc;
			case 5: return emissionInfo.nox;
			case 6: return emissionInfo.pmx;
			case 7: return emissionInfo.noise;
			case 8: return emissionInfo.route;
			case 9: return emissionInfo.type;
			case 10: return emissionInfo.waiting;
			case 11: return emissionInfo.lane;
			case 12: return emissionInfo.pos;
			case 13: return emissionInfo.speed;
			case 14: return emissionInfo.angle;
			case 15: return emissionInfo.x;
                        case 16: return emissionInfo.y;
		}
		return null;
	}
	
	public void setList(List<EmissionInfo> list) {
		this.list = list;
	}


}
