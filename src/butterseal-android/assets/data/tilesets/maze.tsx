<?xml version="1.0" encoding="UTF-8"?>
<tileset name="ice-cave" tilewidth="64" tileheight="64">
 <image source="ice-cave.png" width="1024" height="1024"/>
 <terraintypes>
  <terrain name="snow" tile="-1"/>
 </terraintypes>
 <tile id="9">
  <properties>
   <property name="wall" value="true"/>
  </properties>
 </tile>
 <tile id="10">
  <properties>
   <property name="light" value="torch"/>
  </properties>
 </tile>
 <tile id="11">
  <properties>
   <property name="wall" value="true"/>
  </properties>
 </tile>
 <tile id="12">
  <properties>
   <property name="wall" value="true"/>
  </properties>
 </tile>
 <tile id="13">
  <properties>
   <property name="beacon" value="off"/>
   <property name="light" value="beacon"/>
  </properties>
 </tile>
 <tile id="15">
  <properties>
   <property name="beacon" value="on"/>
   <property name="light" value="beacon"/>
  </properties>
 </tile>
 <tile id="22" terrain="0,0,0,0"/>
 <tile id="25">
  <properties>
   <property name="dark" value="true"/>
  </properties>
 </tile>
 <tile id="40">
  <properties>
   <property name="player" value="ice-cave-exit"/>
  </properties>
 </tile>
 <tile id="98">
  <properties>
   <property name="objective" value="true"/>
  </properties>
 </tile>
 <tile id="255">
  <properties>
   <property name="invisible" value="true"/>
  </properties>
 </tile>
</tileset>
