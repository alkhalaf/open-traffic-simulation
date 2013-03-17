package com.opentrafficsimulation.editor.road;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

//Allow input "."
class MyIntFilter2 extends DocumentFilter {
  
        @Override
		public void insertString(FilterBypass fb, int offset,
                String string, AttributeSet attr)
                throws BadLocationException {
            try {
                if (string.equals(".")
                        && !fb.getDocument()
                                .getText(0, fb.getDocument().getLength())
                                .contains(".")) {
                    super.insertString(fb, offset, string, attr);
                    return;
                }
                Double.parseDouble(string);
                super.insertString(fb, offset, string, attr);
            } catch (Exception e) {
                Toolkit.getDefaultToolkit().beep();
            }

        }

        @Override
        public void replace(FilterBypass fb, int offset, int length,
                String text, AttributeSet attrs)
                throws BadLocationException {
            try {
                if (text.equals(".")
                        && !fb.getDocument()
                                .getText(0, fb.getDocument().getLength())
                                .contains(".")) {
                    super.insertString(fb, offset, text, attrs);
                    return;
                }
                Double.parseDouble(text);
                super.replace(fb, offset, length, text, attrs);
            } catch (Exception e) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

/*
//Allow input integer
class MyIntFilter1 extends DocumentFilter {
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);
		
		if (test(sb.toString())) {
			super.insertString(fb, offset, string, attr);
		} else {
			//warn the user and don't allow the insert
		}
	}
	
	private boolean test(String text) {
		try {
			Integer.parseInt(text); 
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0,doc.getLength()));
		sb.replace(offset, offset + length, text);
		
		if (test(sb.toString())) {
			super.replace(fb, offset, length, text, attrs);
		} else {
			//warn the user and don't allow the insert
		}
		}
		
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.delete(offset, offset+length);
			
			if (test(sb.toString())) {
				super.remove(fb, offset, length);
			} else {
				//warn the user and don't allow the insert
			}
			}
	
}
*/

