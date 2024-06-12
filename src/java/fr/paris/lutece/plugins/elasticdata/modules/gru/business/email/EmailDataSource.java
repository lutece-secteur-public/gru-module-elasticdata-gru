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

package fr.paris.lutece.plugins.elasticdata.modules.gru.business.email;

import java.util.ArrayList;

import fr.paris.lutece.plugins.elasticdata.business.AbstractDataSource;
import fr.paris.lutece.plugins.elasticdata.business.DataObject;
import fr.paris.lutece.plugins.elasticdata.modules.gru.business.BaseDemandObject;
import fr.paris.lutece.plugins.elasticdata.service.DataSourceService;
import fr.paris.lutece.plugins.grubusiness.business.notification.EnumNotificationType;
import fr.paris.lutece.plugins.grubusiness.business.notification.INotificationDAO;
import fr.paris.lutece.plugins.grubusiness.business.notification.INotificationListener;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotificationFilter;
import fr.paris.lutece.plugins.libraryelastic.util.ElasticClientException;
import fr.paris.lutece.portal.service.util.AppLogService;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EmailDataSource
 */
public class EmailDataSource extends AbstractDataSource implements INotificationListener
{
    INotificationDAO _notificationDAO;

    /**
     * Set the INotificationDAO to use
     *
     * @param notificationDAO
     */
    public void setNotificationDAO( INotificationDAO notificationDAO )
    {
        _notificationDAO = notificationDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreateNotification( Notification notification )
    {
        if ( notification == null || notification.getEmailNotification( ) == null )
        {
            return;
        }
        BaseDemandObject notificationObj = new BaseDemandObject( notification );
        try
        {
            DataSourceService.processIncrementalIndexing( this, notificationObj );
        }
        catch( ElasticClientException e )
        {
            AppLogService.error( "ElasticClientException occurs while DataSourceService.insertData for notification [" + notification.getId( ) + "]", e );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpdateNotification( Notification notification )
    {
        AppLogService.info( "EmailDataSource doesn't manage onUpdateNotification method" );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeleteDemand( String strDemandId, String strDemandTypeId )
    {
        AppLogService.info( "EmailDataSource doesn't manage onDeleteDemand method" );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getIdDataObjects( )
    {
        NotificationFilter filter = new NotificationFilter( );
        if ( !filter.containsCustomerEmailNotificationType( ) )
        {
        	filter.getListNotificationType( ).add( EnumNotificationType.CUSTOMER_EMAIL );
        }


        // TODO : replace this deprecated method
        // filter.setHasCustomerEmailNotification( true );
        List<Integer> listIds = new ArrayList<>(); // _notificationDAO.loadIdsByFilter( filter );

        return listIds.stream().map(Object::toString)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DataObject> getDataObjects( List<String> listIdDataObjects )
    {
        List<DataObject> listDataObject = new ArrayList<>( );
        // TODO load all in one database call
        for ( String strId : listIdDataObjects )
        {
            listDataObject.add( new BaseDemandObject( _notificationDAO.loadById( Integer.parseInt( strId ) ).get( ) ) );
        }
        return listDataObject;
    }

}
