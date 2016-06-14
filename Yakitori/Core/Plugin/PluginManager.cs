using System;
using System.Collections.Generic;
using System.IO;
using System.Reflection;

namespace Yakitori.Core.Plugin
{
	public class PluginManager
	{
        private readonly DirectoryInfo PluginDirectory;
        private IDictionary<string, YakitoriPlugin> Plugins = new Dictionary<string, YakitoriPlugin>();
        private IList<PluginInfo> PluginInfoList = new List<PluginInfo>();
        private IPluginLoader Loader;

		public PluginManager(IPluginLoader loader)
		{
            Loader = loader;
			var current = Environment.CurrentDirectory;
			var dir = Path.Combine(current, "plugins");
			if (Directory.Exists(dir))
			{
				PluginDirectory = new DirectoryInfo(dir);
			}
			else
			{
				PluginDirectory = Directory.CreateDirectory(dir);
			}
		}

		public void LoadPlugins()
		{
			foreach (var file in PluginDirectory.GetFiles("*.ypp"))
			{
                LoadPlugin(file);
			}
		}

        private void LoadPlugin(FileInfo file)
        {
            PluginInfo info = Loader.GetPluginInfo(file);
            if (info != null)
            {
                YakitoriPlugin plugin = Loader.LoadPlugin(file, info);
                if (plugin != null)
                {
                    Plugins.Add(info.PluginId, plugin);
                    PluginInfoList.Add(info);
                    plugin.enabled = true;
                    Console.WriteLine("Successful load : " + file.Name);
                    return;
                }
            }
            Console.Error.WriteLine("Failure to load : " + file.Name);
            
        }

        public YakitoriPlugin GetPlugin(string id)
        {
            if (Plugins.ContainsKey(id))
            {
                return Plugins[id];
            }
            return null;
        }

        public IList<PluginInfo> GetAllPluginInfo()
        {
            return new List<PluginInfo>(PluginInfoList);
        }
    }
}

