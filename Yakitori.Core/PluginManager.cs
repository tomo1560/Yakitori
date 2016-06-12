using System;
using System.IO;
using System.Reflection;
namespace Yakitori.Core
{
	public class PluginManager
	{
		readonly DirectoryInfo PluginDirectory;

		public PluginManager()
		{
			var current = Environment.CurrentDirectory;
			var dir = System.IO.Path.Combine(current, "plugins");
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
				var asm = Assembly.LoadFrom(file.FullName);
			}
		}
	}
}

