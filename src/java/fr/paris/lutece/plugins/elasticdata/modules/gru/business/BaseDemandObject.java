package fr.paris.lutece.plugins.elasticdata.modules.gru.business;

import fr.paris.lutece.plugins.elasticdata.business.DataObject;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

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


/**
 * BaseDemandObject
 */
public class BaseDemandObject implements DataObject
{
    private static final String PREFIX = "elasticdata-gru.demand.type_label.";
    private static final String DEFAULT_DEMAND_TYPE = "Not defined";

    private long _lTimestamp;
    private String _strDemandType;
    private String _strDemandTypeId;
    
    @Override
    public String getTimestamp()
    {
        return String.valueOf( _lTimestamp );
    }
    
    /**
     * Set the notification timestamp
     * @param lTimestamp the notification timestamp
     */
    public void setTimestamp( long lTimestamp )
    {
        _lTimestamp = lTimestamp;
    }
    

    /**
     * Returns the DemandType Id
     *
     * @return The DemandType Id
     */
    public String getDemandTypeId()
    {
        return _strDemandTypeId;
    }
    
    /**
     * Set the demand type id
     * @param strDemandTypeId the demand type id
     */
    public void setDemandTypeId( String strDemandTypeId )
    {
        _strDemandTypeId = strDemandTypeId;
        _strDemandType = AppPropertiesService.getProperty( PREFIX + _strDemandTypeId , DEFAULT_DEMAND_TYPE );
    }
    
    /**
     * Returns the DemandType
     *
     * @return The DemandType
     */
    public String getDemandType()
    {
        return _strDemandType;
    }
  
}

