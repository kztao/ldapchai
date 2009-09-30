/*
 * LDAP Chai API
 * Copyright (c) 2006-2009 Novell, Inc.
 * Copyright (c) 2009 Jason D. Rivard
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.novell.ldapchai.impl.ad.entry;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ADEntries {

    final static long AD_EPOCH_OFFSET_MS;

    static {
        final Calendar msEpochCalender = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        msEpochCalender.clear();
        msEpochCalender.set(1601, 0, 1, 0, 0);
        AD_EPOCH_OFFSET_MS = msEpochCalender.getTime().getTime();
    }

    private ADEntries() {
    }

    /**
     * Convert a Date to the Zulu String format.
     * See the <a href="http://developer.novell.com/documentation/ndslib/schm_enu/data/sdk5701.html">eDirectory Time attribute syntax definition</a> for more details.
     *
     * @param date The Date to be converted
     * @return A string formated such as "199412161032Z".
     */
    public static String convertDateToWinEpoch(final Date date)
    {
        if (date == null) {
            throw new NullPointerException("date must be non-null");
        }

        long inputAsMs = date.getTime();
        long inputAsADMs = inputAsMs - AD_EPOCH_OFFSET_MS;
        long inputAsADNs = inputAsADMs * 10000;

        return String.valueOf(inputAsADNs);
    }

    public static Date convertWinEpochToDate(final String dateString)
    {
        if (dateString == null) {
            throw new NullPointerException("dateString must be non-null");
        }

        final long timestampAsNs = Long.parseLong(dateString);
        final long timestampAsMs = timestampAsNs / 10000;
        final long timestampAsJavaMs = timestampAsMs + AD_EPOCH_OFFSET_MS;

        return new Date(timestampAsJavaMs);
    }
}
