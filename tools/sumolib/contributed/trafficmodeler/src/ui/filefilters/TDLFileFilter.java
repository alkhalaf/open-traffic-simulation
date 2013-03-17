<<<<<<< HEAD
package ui.filefilters;

import java.io.File;

/**
 * Filter used in the open / save project dialogs
 *
 */
public class TDLFileFilter extends javax.swing.filechooser.FileFilter{

	@Override
	public boolean accept(File f) {
		if((f.isDirectory())||(f.getName().endsWith(".tdl"))){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public String getDescription() {
		return "Traffic definition language file (*.tdl)";
	}
=======
package ui.filefilters;

import java.io.File;

/**
 * Filter used in the open / save project dialogs
 *
 */
public class TDLFileFilter extends javax.swing.filechooser.FileFilter{

	@Override
	public boolean accept(File f) {
		if((f.isDirectory())||(f.getName().endsWith(".tdl"))){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public String getDescription() {
		return "Traffic definition language file (*.tdl)";
	}
>>>>>>> origin/abdalla
}