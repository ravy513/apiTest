/*******************************************************************************
 * Copyright (c) 2010-2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *  dclarke - EclipseLink 2.3 - MySports Demo Bug 344608
 ******************************************************************************/
package eclipselink.example.mysports.application.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;

/**
 * JSF managed bean displaying a team.
 * 
 * @author dclarke
 * @since EclipseLink 2.3.0
 */
@ManagedBean(name=ViewTeam.NAME)
@SessionScoped
public class ViewTeam extends BaseTeamBean {

    protected static final String NAME = "viewTeam";

    protected static final String PAGE = "view-team?faces-redirect=true";

    @Override
    public String getPage() {
        return PAGE;
    }

    @Override
    public HtmlDataTable getDataTable() {
        return getDataTable(NAME, false);
    }

}
