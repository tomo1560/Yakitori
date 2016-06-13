using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;

namespace Yakitori.Core.Plugin
{
	public class PluginManager
	{
        public static readonly PluginManager Instance = new PluginManager();

		private readonly DirectoryInfo PluginDirectory;
        private Dictionary<string, PluginBase> Plugins = new Dictionary<string, PluginBase>();

		private PluginManager()
		{
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
			foreach (var file in PluginDirectory.GetFiles("*.dll"))
			{
                LoadPlugin(file);
			}
		}

        private void LoadPlugin(FileInfo file)
        {
            var asm = Assembly.LoadFrom(file.FullName);
            try
            {
                YakitoriPluginClass main = asm.GetCustomAttribute<YakitoriPluginClass>();
                if (main == null)
                {
                    return;
                }
                PluginBase plugin = asm.CreateInstance(main.PluginClass) as PluginBase;
                if (plugin != null)
                {
                    Plugins.Add(plugin.PluginID, plugin);
                    plugin.SetEnable();
                }
            }
            catch {}
        }

        public PluginBase GetPlugin(string id)
        {
            if (Plugins.ContainsKey(id))
            {
                return Plugins[id];
            }
            return null;
        }

        public IList<PluginBase> GetLoadedPlugins()
        {
            return Plugins.Values.ToList();
        }
    }
}

