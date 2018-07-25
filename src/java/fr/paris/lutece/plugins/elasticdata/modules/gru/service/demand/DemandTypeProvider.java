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

import fr.paris.lutece.plugins.crmclient.util.CRMException;
import fr.paris.lutece.plugins.elasticdata.business.IDataSourceExternalAttributesProvider;
import fr.paris.lutece.plugins.elasticdata.modules.gru.business.BaseDemandObject;
import fr.paris.lutece.plugins.elasticdata.modules.gru.business.demand.DemandDataSource;
import fr.paris.lutece.portal.service.util.AppLogService;
import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;

/**
 * Provider for demand types external attributes
 */
public class DemandTypeProvider implements IDataSourceExternalAttributesProvider<DemandDataSource>
{
    @Inject
    DemandTypeService _demandTypeService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void provideAttributes( DemandDataSource dataSource )
    {
        if ( dataSource instanceof DemandDataSource )
        {
            try
            {
                Map<String, String> listDemandTypes = _demandTypeService.getDemandTypes( );

                for ( Object demandObject : dataSource.getDataObjects( ) )
                {
                    BaseDemandObject baseDemandObject = (BaseDemandObject)demandObject;
                    baseDemandObject.setDemandType( listDemandTypes.get( baseDemandObject.getDemandTypeId( ) ) );
                }
            }
            catch( CRMException e )
            {
                AppLogService.error( "Unable to get remote demand types " + e.getMessage( ), e );
            }
            catch( IOException e )
            {
                AppLogService.error( "Unable to parse DemandTypes json " + e.getMessage( ), e );
            }
        }
    }
}
