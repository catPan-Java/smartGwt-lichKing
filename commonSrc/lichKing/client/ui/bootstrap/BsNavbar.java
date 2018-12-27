package lichKing.client.ui.bootstrap;

import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;

public class BsNavbar {

    public static HTMLFlow create(){
    	
    	HTMLFlow panel = new HTMLFlow();
    	panel.setStyleName("main-header");
    	
    	HLayout header=new HLayout();

    	Layout logo=new Layout();
    	logo.setStyleName("logo");
    	logo.setContents("<b>SGwt</b> SYS");
    	header.addMember(logo);
    	
    	HLayout navbar=new HLayout();
    	navbar.setStyleName("navbar navbar-static-top");

    	HLayout menu=new HLayout();
    	menu.setStyleName("navbar-custom-menu");
    	
    	HTMLFlow icon1 = new HTMLFlow();
    	String str1="<ul class='nav navbar-nav'>"
			    		+ "<li class='dropdown messages-menu'>"
			    			+ "<a href='#' class='dropdown-toggle'>"
			    			+ "<i class='fa fa-envelope-o'></i>"
			    			+ "<span class='label label-success'>4</span>"
			    			+ "</a>"
						+ "</li>"
					+ "</ul>";
    	icon1.setContents(str1);
    	menu.addChild(icon1);

//    	UListElement ul=Document.get().createULElement();
//    	LIElement li=Document.get().createLIElement();
//        li.appendChild(icon1.getElement());
//        ul.appendChild(li);
//    	menu.addChild(ul);
    	

    	navbar.addMember(menu);
    	header.addMember(navbar);
    	
    	panel.addChild(header);
    	return panel;
    	
/*    	
    	String contents="<header class='main-header'>"
    			+ "<div class='logo'><b>SGwt</b> SYS</div>"
    			+ "<nav class='navbar navbar-static-top' role='navigation'>"
	    			+ "<div class='navbar-custom-menu'>"
		    			+ "<ul class='nav navbar-nav'>"
			    			+ "<li class='dropdown messages-menu'>"
				    			+ "<a href='#' class='dropdown-toggle'>"
				    			+ "<i class='fa fa-envelope-o'></i>"
				    			+ "<span class='label label-success'>4</span>"
				    			+ "</a>"
			    			+ "</li>"
		    			+ "</ul>"
	    			+ "</div>"
    			+ "</nav>"
    			+ "</header>";
    	
    	HTMLFlow panel = new HTMLFlow();
    	panel.setContents(contents);
    	return panel;*/
    }
}
