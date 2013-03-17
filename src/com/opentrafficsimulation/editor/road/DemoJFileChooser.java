package com.opentrafficsimulation.editor.road;
import java.io.File;

import javax.swing.filechooser.FileFilter;
class ExtensionFileFilter extends FileFilter {
	  String description;

	  String extensions[];

	  public ExtensionFileFilter(String description, String extension) {
	    this(description, new String[] { extension });
	  }

	  public ExtensionFileFilter(String description, String extensions[]) {
	    if (description == null) {
	      //this.description = extensions[0] + "{ " + extensions.length + "} ";
	      this.description = extensions[0];
	    } else {
	      this.description = description;
	    }
	    this.extensions = extensions.clone();
	    toLower(this.extensions);
	  }

	  private void toLower(String array[]) {
	    for (int i = 0, n = array.length; i < n; i++) {
	      array[i] = array[i].toLowerCase();
	    }
	  }

	  @Override
	public String getDescription() {
	    return description;
	  }

	  @Override
	public boolean accept(File file) {
	    if (file.isDirectory()) {
	      return true;
	    } else {
	      String path = file.getAbsolutePath().toLowerCase();
	      for (int i = 0, n = extensions.length; i < n; i++) {
	        String extension = extensions[i];
	        if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
	          return true;
	        }
	      }
	    }
	    return false;
	  }
	}
/*
public class DemoJFileChooser extends JPanel
   implements ActionListener {
   JButton go;
    
   JFileChooser chooser;
   String choosertitle;
    
  public DemoJFileChooser() {
    go = new JButton("Do it");
    go.addActionListener(this);
    add(go);
   }
 
  public void actionPerformed1(ActionEvent e) {
    int result;
         
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // disable the "All files" option.
    //
    FileFilter jpegFilter = new ExtensionFileFilter(null, new String[] { "JPG", "JPEG" });

    chooser.addChoosableFileFilter(jpegFilter);
    //chooser.setAcceptAllFileFilterUsed(true);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
      System.out.println("getCurrentDirectory(): "
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : "
         +  chooser.getSelectedFile());
      }
    else {
      System.out.println("No Selection ");
      }
     }
    
  public Dimension getPreferredSize(){
    return new Dimension(200, 200);
    }
  
  public static void main(String s[]) {
    JFrame frame = new JFrame("test");
    DemoJFileChooser panel = new DemoJFileChooser();
    frame.addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          System.exit(0);
          }
        }
      );
    frame.getContentPane().add(panel,"Center");
    frame.setSize(panel.getPreferredSize());
    frame.setVisible(true);
    }

  
  
  
@Override
public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	 int result;
     
	    chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(choosertitle);
	    
	    FileFilter jpegFilter = new ExtensionFileFilter(null, new String[] {"*.osm.xml"});

	    chooser.addChoosableFileFilter(jpegFilter);
	    //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    //
	    // disable the "All files" option.
	    //
	    chooser.setAcceptAllFileFilterUsed(false);
	    //    
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
	      System.out.println("getCurrentDirectory(): "
	         +  chooser.getCurrentDirectory().getPath());
	      System.out.println("getSelectedFile() : "
	         +  chooser.getSelectedFile());
	      }
	    else {
	      System.out.println("No Selection ");
	      }
}


}*/