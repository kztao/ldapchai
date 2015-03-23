# Introduction #

Overview and summary of the LDAP Chai API design guidelines and principals

# Goals #
  * Provide pluggable interface to the "real" java ldap apis (JNDI, JLDAP, etc)
  * Provide vendor abstraction, releasing users of the API from focusing on vendor specific code
  * Support only atomic LDAP operations.  Each call to the API must result in immediate return of data.  This is to facilitate fail over, load balancing, etc.