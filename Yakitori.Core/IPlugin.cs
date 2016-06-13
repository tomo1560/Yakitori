namespace Yakitori.Core
{
    interface IPlugin
    {
        string PluginID { get; }

        public void OnEnable();
        public void OnDisable();
    }
}
