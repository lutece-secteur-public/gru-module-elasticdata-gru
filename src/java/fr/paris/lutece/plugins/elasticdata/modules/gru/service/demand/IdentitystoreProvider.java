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

import fr.paris.lutece.plugins.elasticdata.business.DataObject;
import fr.paris.lutece.plugins.elasticdata.business.DataSource;
import fr.paris.lutece.plugins.elasticdata.business.IDataSourceExternalAttributesProvider;
import fr.paris.lutece.plugins.elasticdata.modules.gru.business.BaseDemandObject;
import fr.paris.lutece.plugins.identitystore.v1.web.rs.dto.IdentityDto;
import fr.paris.lutece.plugins.identitystore.v1.web.service.IdentityService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Provider for demand types external attributes
 */
public class IdentitystoreProvider implements IDataSourceExternalAttributesProvider
{
    private static final String PROPERTY_APPLICATION_CODE = AppPropertiesService.getProperty( "elasticdata-gru.identitystore.applicationCode" );
    private static final String PROPERTY_IDENTITY_ATTRIBUTES_CODE_KEY = "elasticdata-gru.identitystore.attributeCodeToIndex.";

    @Inject
    DemandTypeService _demandTypeService;
    @Inject
    IdentityService _identityService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void provideAttributes( DataSource dataSource )
    {
        if ( !dataSource.getDataObjects( ).isEmpty( ) )
        {
            for ( DataObject dataObject : dataSource.getDataObjects( ) )
            {
                provideAttributes( dataObject );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void provideAttributes( DataObject dataObject )
    {
        if ( dataObject != null && dataObject instanceof BaseDemandObject )
        {
            BaseDemandObject demandDataObject = (BaseDemandObject) dataObject;
            provideIdentityAttributes( demandDataObject );
        }
    }

    /**
     * Provide attributes from identitystore in a dataObject
     * 
     * @param dataObject
     *            The data Object
     */
    private void provideIdentityAttributes( BaseDemandObject dataObject )
    {
        String strConnectionId = dataObject.getConnectionId( );
        if ( !strConnectionId.isEmpty( ) )
        {
            IdentityDto identity = _identityService.getIdentityByConnectionId( strConnectionId, PROPERTY_APPLICATION_CODE );
            List<String> listIdentityAttributesCodeKeys = AppPropertiesService.getKeys( PROPERTY_IDENTITY_ATTRIBUTES_CODE_KEY );
            Map<String, String> mapIdentityAttribute = new HashMap<>( );

            for ( String strIdentityAttributeCodeKey : listIdentityAttributesCodeKeys )
            {
                String strIdentityAttributeCode = AppPropertiesService.getProperty( strIdentityAttributeCodeKey );
                if ( identity.getAttributes( ).keySet( ).contains( strIdentityAttributeCode ) )
                {
                    mapIdentityAttribute.put( strIdentityAttributeCode, identity.getAttributes( ).get( strIdentityAttributeCode ).getValue( ) );
                }
            }
            dataObject.setCustomerIdentityAttributes( mapIdentityAttribute );
        }

    }
}
