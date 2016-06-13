using System;

namespace Yakitori.Core.Plugin.Attribute
{
    class YakitoriPluginID : System.Attribute
    {
        public readonly string PluginID;

        public YakitoriPluginID(string id)
        {
            PluginID = id;
        }
    }
}
