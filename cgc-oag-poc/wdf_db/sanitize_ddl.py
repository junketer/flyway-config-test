
# coding: utf-8

# In[77]:


import re
import sys

# In[78]:


f = open(sys.argv[1])


# In[79]:


#pattern = r"(\))(\s*IN\s*[0-9A-Z_]+\s*)(;)"


# In[80]:


xmlpattern = "([A-Za-z0-9_]+)\s+DB2XML\.XMLVARCHAR"


# In[81]:


ff = f.readlines()


# In[82]:


fff = "".join(ff)


# In[83]:


#no_tablespace = re.sub(pattern, lambda m: m.group(1) + m.group(3), fff)
colw = {'BOOKING_CLASS_XML':	1000, 'CONFIG_CLASS_XML':	1000, 'DUPLICATE_LEG_XML':	5000, 'OPTIONAL_ELEMENT_XML':	500, 'PARTICIPANT_XML':	5000, 'PARTNERSHIP_XML':	200, 'TRAFFIC_RESTRICTION_XML':	500 }

# In[84]:
def repl(match):
	return match.group(1) + ' VARCHAR({})'.format(colw[match.group(1).upper()])

no_xml_extender = re.sub(xmlpattern, repl, fff)


# In[85]:


#no_snap_comm_table = re.sub(snapcommeventpattern, '', no_xml_extender)


# In[86]:


with open(sys.argv[2], 'w+') as fprocessed:
    fprocessed.write(no_xml_extender)

