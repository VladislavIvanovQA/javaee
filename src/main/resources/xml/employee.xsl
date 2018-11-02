<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/">
        <table border="1">
            <tr bgcolor="#9acd32">
                <th>№</th>
                <th>Name</th>
                <th>Job</th>
                <th>Salary</th>
            </tr>
            <xsl:for-each select="users/user">
                <xsl:choose>
                    <xsl:when test="@maxAverage = 'up'">
                        <tr bgcolor="#a0a19a">
                            <td><xsl:value-of select="@userno"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="job"/></td>
                            <td><xsl:value-of select="sal"/></td>
                        </tr>
                    </xsl:when>
                    <xsl:otherwise>
                        <tr>
                            <td><xsl:value-of select="@userno"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="job"/></td>
                            <td><xsl:value-of select="sal"/></td>
                        </tr>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
        </table>
    </xsl:template>
</xsl:stylesheet>