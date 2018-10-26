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

package fr.paris.lutece.plugins.elasticdata.modules.gru.business;

import fr.paris.lutece.plugins.elasticdata.business.AbstractDataObject;
import fr.paris.lutece.plugins.grubusiness.business.demand.Demand;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * BaseDemandObject
 */
public class BaseDemandObject extends AbstractDataObject
{
    private static final String DEFAULT_DEMAND_TYPE = "Not defined";
    private static final String PREFIX_DEMAND_SUBTYPE = "elasticdata-gru.demand.subtype_label.";
    private static final String DEFAULT_DEMAND_SUBTYPE = "Not defined";

    private String _strDemandType;
    private String _strConnectionId;
    private String _strDemandTypeId;
    private String _strDemandSubtype;
    private String _strDemandSubtypeId;
    private long _lTimestampClosure;
    private String _strDayOfWeekClosure;
    private String _strMonthClosure;
    private String _strHourClosure;
    private String _strPrefixedDayOfWeekClosure;
    private String _strPrefixedMonthClosure;
    private Map<String,String> _mapCustomerIdentityAttributes;

    /**
     * Base constructor
     */
    public BaseDemandObject( )
    {
        super( );
        _strDemandType = DEFAULT_DEMAND_TYPE;
        _strDemandTypeId = "";
        _strDemandSubtype = DEFAULT_DEMAND_SUBTYPE;
        _strDemandSubtypeId = "";
        _strConnectionId = "";
        _mapCustomerIdentityAttributes = new HashMap<>();
        _strDayOfWeekClosure = "";
        _strMonthClosure="";
        _strHourClosure="";
        _strPrefixedDayOfWeekClosure="";
        _strPrefixedMonthClosure="";
        _lTimestampClosure=0;
    }

    /**
     * Constructor with Demand
     * 
     * @param demand
     *            The demand
     */
    public BaseDemandObject( Demand demand )
    {
        super( );
        if ( demand != null )
        {
            _strDemandTypeId = demand.getTypeId( );
            _strDemandSubtypeId = demand.getSubtypeId( );
            _strConnectionId = demand.getCustomer( ).getId( );
            _strDemandSubtype = AppPropertiesService.getProperty( PREFIX_DEMAND_SUBTYPE + _strDemandSubtypeId, DEFAULT_DEMAND_SUBTYPE );
            setTimestamp( demand.getCreationDate( ) );
            _lTimestampClosure = demand.getClosureDate();
            Locale locale = LocaleService.getDefault();
            Calendar calendar = Calendar.getInstance( locale );
            calendar.setTimeInMillis( _lTimestampClosure );
            _strDayOfWeekClosure = calendar.getDisplayName( Calendar.DAY_OF_WEEK, Calendar.LONG , locale );
            _strPrefixedDayOfWeekClosure = ((( calendar.get( Calendar.DAY_OF_WEEK ) + 5 ) % 7 ) + 1 ) + " - " + _strDayOfWeekClosure;
            _strMonthClosure = calendar.getDisplayName( Calendar.MONTH, Calendar.LONG , locale );
            _strPrefixedMonthClosure = String.format( "%02d" , calendar.get( Calendar.MONTH ) + 1 ) + " - " + _strMonthClosure;
            _strHourClosure = String.format( "%02d" , calendar.get(Calendar.HOUR_OF_DAY ));
        }
    }

    /**
     * Constructor with Notification
     * 
     * @param notification
     *            The notification
     */
    public BaseDemandObject( Notification notification )
    {
        super( );
        if ( notification != null )
        {
            setTimestamp( notification.getDate( ) );
            if ( notification.getDemand( ) != null )
            {
                _strDemandTypeId = notification.getDemand( ).getTypeId( );
                _strDemandSubtypeId = notification.getDemand( ).getSubtypeId( );
                _strDemandSubtype = AppPropertiesService.getProperty( PREFIX_DEMAND_SUBTYPE + _strDemandSubtypeId, DEFAULT_DEMAND_SUBTYPE );
                _strConnectionId = notification.getDemand( ).getCustomer( ).getId( );
                _lTimestampClosure = notification.getDemand().getClosureDate();
                Locale locale = LocaleService.getDefault();
                Calendar calendar = Calendar.getInstance( locale );
                calendar.setTimeInMillis( _lTimestampClosure );
                _strDayOfWeekClosure = calendar.getDisplayName( Calendar.DAY_OF_WEEK, Calendar.LONG , locale );
                _strPrefixedDayOfWeekClosure = ((( calendar.get( Calendar.DAY_OF_WEEK ) + 5 ) % 7 ) + 1 ) + " - " + _strDayOfWeekClosure;
                _strMonthClosure = calendar.getDisplayName( Calendar.MONTH, Calendar.LONG , locale );
                _strPrefixedMonthClosure = String.format( "%02d" , calendar.get( Calendar.MONTH ) + 1 ) + " - " + _strMonthClosure;
                _strHourClosure = String.format( "%02d" , calendar.get(Calendar.HOUR_OF_DAY ));
            }
        }
    }

    /**
     * Returns the DemandType Id
     *
     * @return The DemandType Id
     */
    public String getDemandTypeId( )
    {
        return _strDemandTypeId;
    }

    /**
     * Set the demand type id
     * 
     * @param strDemandTypeId
     *            the demand type id
     */
    public void setDemandTypeId( String strDemandTypeId )
    {
        _strDemandTypeId = strDemandTypeId;
    }

    /**
     * Returns the DemandType
     *
     * @return The DemandType
     */
    public String getDemandType( )
    {
        return _strDemandType;
    }

    /**
     * Set the demand type
     * 
     * @param strDemandType
     */
    public void setDemandType( String strDemandType )
    {
        _strDemandType = strDemandType;
    }

    /**
     * Returns the DemandSubtype Id
     *
     * @return The DemandSubtype Id
     */
    public String getDemandSubtypeId( )
    {
        return _strDemandSubtypeId;
    }

    /**
     * Set the demand type id
     * 
     * @param strDemandSubtypeId
     *            the demand type id
     */
    public void setDemandSubtypeId( String strDemandSubtypeId )
    {
        _strDemandSubtypeId = strDemandSubtypeId;
        _strDemandSubtype = AppPropertiesService.getProperty( PREFIX_DEMAND_SUBTYPE + _strDemandSubtypeId, DEFAULT_DEMAND_SUBTYPE );
    }

    /**
     * Returns the DemandSubtype
     *
     * @return The DemandSubtype
     */
    public String getDemandSubtype( )
    {
        return _strDemandSubtype;
    }

    /**
     * Get the connection id of the user of the demand
     * @return the connection id
     */
    public String getConnectionId() {
        return _strConnectionId;
    }

    /**
     * Set the connection id of the user of the demand
     * @param strConnectionId the connection id
     */
    public void setConnectionId(String strConnectionId)
    {
        _strConnectionId = strConnectionId;
    }

    /**
     * Get customer identity attributes
     * @return the map of customer identity attributes
     */
    public Map<String, String> getCustomerIdentityAttributes() {
        return _mapCustomerIdentityAttributes;
    }

    /**
     * Set the customer identity attributes map
     * @param mapCustomerIdentityAttributes 
     */
    public void setCustomerIdentityAttributes (Map<String, String> mapCustomerIdentityAttributes ) 
    {
        _mapCustomerIdentityAttributes = mapCustomerIdentityAttributes;
    }

    /**
     * Get the timestamp of the closure of the demand
     * @return The timestamp of the closure of the demand
     */
    public String getTimestampClosure() 
    {
        return String.valueOf(_lTimestampClosure);
    }

    /**
     * Get the day of week of the closure of the demand
     * @return the day of week of the closure of the demand
     */
    public String getDayOfWeekClosure() 
    {
        return _strDayOfWeekClosure;
    }

    /**
     * Get the month of the closure of the demand
     * @return the month of the closure of the demand
     */
    public String getMonthClosure() 
    {
        return _strMonthClosure;
    }

    /**
     * Get the hour of the closure of the demand
     * @return the hour of the closure of the demand
     */
    public String getHourClosure() {
        return _strHourClosure;
    }

    /**
     * Get the prefixed day of week of the closure of the demand
     * @return the prefixed day of week of the closure of the demand
     */
    public String getPrefixedDayOfWeekClosure() {
        return _strPrefixedDayOfWeekClosure;
    }

    /**
     * Get the prefixed month of the closure of the demand
     * @return the prefixed month of the closure of the demand
     */
    public String getPrefixedMonthClosure() {
        return _strPrefixedMonthClosure;
    }
    
    

}
