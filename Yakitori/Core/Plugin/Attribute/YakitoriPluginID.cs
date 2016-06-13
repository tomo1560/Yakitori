using System;

namespace Yakitori.Core.Plugin.Attribute
{
    [AttributeUsage(AttributeTargets.Assembly)]
    class YakitoriPluginID : System.Attribute
    {
        public readonly string PluginID;

        public YakitoriPluginID(string id)
        {
            PluginID = id;
        }
    }
}
