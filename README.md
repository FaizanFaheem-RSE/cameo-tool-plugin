# Cameo-Tool-Plugin

## Overview
This plugin couples SysML models created in Cameo Systems Modeler (MagicDraw-based) with a Knowledge Graph to support potential failure analysis of technical products during the concept phase of product development.

## Development Environment Setup
1. Set up the development environment by following the official link: https://docs.nomagic.com/spaces/MD2024x/pages/136715054/Plugins
2. Launch Eclipse IDE.
3. Import the developed Plugin in the Eclipse workspace.
4. Configure the MagicDraw Installation Directory:
   - Right-click **MagicDraw** in the Project Explorer.
   - Select **MAGIC_DRAW_INSTALL_DIRECTORY**.
   - Click **Properties**.
   - Edit the installation path if required.
   - Click **Apply and Close**.
5. Configure the Java Build Path for the plugin:
   - Right-click **Plugin Folder Name** and go to **Properties**.
   - Under **Properties**, navigate to **Java Build Path → Libraries**.
   - Identify all build path missing entries.
   - Select each missing library entry and click **Edit**.
6. Click on **Project folder** name than go to File ⇾ Export.
7. Choose the Jar file as export and the export location.
8. Copy the jar and the XML file into Cameo Systems Modeler/plugins/myplugin.
9. Open Cameo/MagicDraw and the plugin is loaded.

## Potential Failure Analysis in Cameo Systems Modeler
<p align="center"><img width="700" alt="Potential Failure Analysis" src="https://github.com/user-attachments/assets/9fd4efa5-a392-48ef-9ece-16686ff93389" /></p>


  
