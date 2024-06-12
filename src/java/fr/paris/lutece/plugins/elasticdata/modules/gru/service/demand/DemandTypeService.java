/*
 * Copyright (c) 2002-2018, Mairie de Paris
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
package fr.paris.lutece.plugins.elasticdata.modules.gru.service.demand;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import fr.paris.lutece.plugins.grubusiness.business.demand.DemandType;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import fr.paris.lutece.plugins.notificationstore.v1.web.service.NotificationStoreService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

/**
 * The demand type service
 */
public class DemandTypeService
{
    private static final String PROPERTY_NB_MINUTES_BEFORE_FETCHING_DEMANDTYPE = "elasticdata-gru.demandType.delayForNewFetch";

    NotificationStoreService _notificationStoreService;

    Map<String, String> _mapDemandType;
    Instant _instantLastFetchingDemandType;

    /**
     * constructor
     * 
     * @param notificationStoreService
     */
    public DemandTypeService( NotificationStoreService notificationStoreService )
    {
        super( );
        this._notificationStoreService = notificationStoreService;
    }

    /**
     * Get the demand types map ( stored or fetched )
     * 
     * @return the map of demand type ( id / label )
     * @throws CRMException
     *             The CRM exception
     * @throws IOException
     *             The IO exeption
     * @throws NotificationException
     */
    public Map<String, String> getDemandTypes( ) throws IOException, NotificationException
    {
        if ( _instantLastFetchingDemandType == null )
        {
            _mapDemandType = _notificationStoreService.getDemandTypes( ).stream( )
                    .collect( Collectors.toMap( d -> String.valueOf( d.getIdDemandType( ) ), DemandType::getLabel, ( a, b ) -> a ) );
            _instantLastFetchingDemandType = Instant.now( );
        }
        else
        {
            Instant iNow = Instant.now( );
            Duration between = Duration.between( _instantLastFetchingDemandType, iNow );
            long nMinBeforeFetchingAgain = AppPropertiesService.getPropertyLong( PROPERTY_NB_MINUTES_BEFORE_FETCHING_DEMANDTYPE, 60 );

            if ( between.toMinutes( ) > nMinBeforeFetchingAgain || _mapDemandType == null || _mapDemandType.isEmpty( ) )
            {
                _mapDemandType = _notificationStoreService.getDemandTypes( ).stream( )
                        .collect( Collectors.toMap( d -> String.valueOf( d.getIdDemandType( ) ), DemandType::getLabel, ( a, b ) -> a ) );
            }

            _instantLastFetchingDemandType = Instant.now( );
        }

        return _mapDemandType;
    }
}
