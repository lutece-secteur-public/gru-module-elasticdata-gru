/*
 * Copyright (c) 2002-2017, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */

package fr.paris.lutece.plugins.elasticdata.modules.gru.business.notification;

import fr.paris.lutece.plugins.elasticdata.business.AbstractDataSource;
import fr.paris.lutece.plugins.elasticdata.business.DataObject;
import fr.paris.lutece.plugins.elasticdata.business.IndexerAction;
import fr.paris.lutece.plugins.elasticdata.modules.gru.business.BaseDemandObject;
import fr.paris.lutece.plugins.elasticdata.service.DataSourceIncrementalService;
import fr.paris.lutece.plugins.grubusiness.business.demand.Demand;
import fr.paris.lutece.plugins.grubusiness.business.notification.INotificationDAO;
import fr.paris.lutece.plugins.grubusiness.business.notification.INotificationListener;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotificationFilter;
import fr.paris.lutece.plugins.notificationstore.business.DemandHome;
import fr.paris.lutece.plugins.notificationstore.business.NotificationHome;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * NotificationDataSource
 */
public class NotificationDataSource extends AbstractDataSource implements INotificationListener
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreateNotification( Notification notification )
    {
        if ( notification == null )
        {
            return;
        }
        BaseDemandObject notificationObj = new BaseDemandObject( notification );
        DataSourceIncrementalService.addTask( this.getId( ), String.valueOf( notificationObj.getId( ) ), IndexerAction.TASK_CREATE );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpdateNotification( Notification notification )
    {
        AppLogService.info( this.getClass().getName() + " doesn't manage onUpdateNotification method" );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeleteDemand( String strDemandId, String strDemandTypeId )
    {
        AppLogService.info( this.getClass().getName() + " doesn't manage onDeleteDemand method" );
    }

    @Override
    public List<String> getIdDataObjects( )
    {
        
    	/* * avoid returning all DB ids *
    	NotificationFilter filter = new NotificationFilter( );
        List<Integer> listIds = _notificationDAO.loadIdsByFilter( filter );
        return listIds.stream( ).map( Object::toString ).collect( Collectors.toList( ) ); */
    	
    	return new ArrayList<String>();
    }

    @Override
    public List<DataObject> getDataObjects( List<String> listIdDataObjects )
    {
        List<DataObject> listDataObject = new ArrayList<>( );
        List<Integer> listIdAsInt = listIdDataObjects.stream( ).map(Integer::valueOf).collect(Collectors.toList());
        
        List<Notification> notifList = NotificationHome.getByIds( listIdAsInt );
        
        notifList.stream( ).forEach( d -> listDataObject.add( new BaseDemandObject( d ) ) );

        return listDataObject;
    }
}
