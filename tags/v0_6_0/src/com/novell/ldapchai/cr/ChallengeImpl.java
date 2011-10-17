/*
 * LDAP Chai API
 * Copyright (c) 2006-2010 Novell, Inc.
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

package com.novell.ldapchai.cr;

import java.io.Serializable;

/**
 *
 */
class ChallengeImpl implements Challenge, Serializable {
// ------------------------------ FIELDS ------------------------------

    private boolean adminDefined;
    private boolean required;
    private String challengeText;

    private int minLength;
    private int maxLength;

    private boolean locked;

// --------------------------- CONSTRUCTORS ---------------------------

    public ChallengeImpl(
            final boolean required,
            final String challengeText,
            final int minLength,
            final int maxLength,
            final boolean adminDefined
    )
    {
        this.adminDefined = adminDefined;
        this.required = required;
        this.challengeText = challengeText;
        this.minLength = minLength < 0 ? 2 : minLength;
        this.maxLength = maxLength < 0 ? 255 : maxLength;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public final String getChallengeText()
    {
        return challengeText;
    }

    public void setChallengeText(final String challengeText)
    {
        if (isLocked()) {
            throw new IllegalStateException("challenge is locked, modification not permitted");
        }

        if (isAdminDefined()) {
            throw new IllegalArgumentException("challenge is admin defined, challengeText not modifiyable");
        }

        this.challengeText = challengeText;
    }

    public final int getMaxLength()
    {
        return maxLength;
    }

    public final int getMinLength()
    {
        return minLength;
    }

    public final boolean isAdminDefined()
    {
        return adminDefined;
    }

    public boolean isLocked()
    {
        return locked;
    }

    public final boolean isRequired()
    {
        return required;
    }

// ------------------------ CANONICAL METHODS ------------------------

    /**
     * Tests for equality of Challenges.  Challenges are equal when the following elements of a chellenge are equal:
     * <ul>
     *   <li>admin defined</li>
     *   <li>maximum length</li>
     *   <li>minimum length</li>
     *   <li>required</li>
     *   <li>challenge text if admin defined is true</li>
     * </ul>
     * Specifically, the response text is not used to test equality.
     *
     * @param o another {@link com.novell.ldapchai.cr.Challenge} object
     * @return true if the objects are the same.
     */
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ChallengeImpl challenge = (ChallengeImpl) o;

        if (adminDefined != challenge.adminDefined) return false;
        if (maxLength != challenge.maxLength) return false;
        if (minLength != challenge.minLength) return false;
        if (required != challenge.required) return false;
        if (adminDefined) {
            if (challengeText != null ? !challengeText.equals(challenge.challengeText) : challenge.challengeText != null) {
                return false;
            }
        }


        return true;
    }



    public int hashCode()
    {
        int result;
        result = (adminDefined ? 1 : 0);
        result = 31 * result + (required ? 1 : 0);
        result = 31 * result + (challengeText != null ? challengeText.hashCode() : 0);
        result = 31 * result + minLength;
        result = 31 * result + maxLength;
        return result;
    }

    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append("Challenge: ");
        if (getChallengeText() != null && getChallengeText().length() > 0) {
            sb.append('\"').append(getChallengeText()).append('\"');
        } else {
            sb.append("[undefined]");
        }
        sb.append(", required: ").append(String.valueOf(isRequired()));
        sb.append(", adminDefined: ").append(String.valueOf(isAdminDefined()));
        sb.append(", minLength: ").append(getMinLength());
        sb.append(", maxLength: ").append(getMaxLength());

        return sb.toString();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Challenge ---------------------

    public void lock()
    {
        locked = true;
    }
}
