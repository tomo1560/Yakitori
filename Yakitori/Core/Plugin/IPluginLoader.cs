using System.IO;

namespace Yakitori.Core.Plugin
{
    public interface IPluginLoader
    {
        PluginInfo GetPluginInfo(FileInfo file);

        YakitoriPlugin LoadPlugin(FileInfo file, PluginInfo info);
    }
}
