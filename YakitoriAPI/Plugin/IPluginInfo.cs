namespace Yakitori.API.Plugin
{
    public interface IPluginInfo
    {
        string DLLFile { get; }
        string MainClass { get; }
        string PluginID { get; }
        string Name { get; }
        string Description { get; }
        string Version { get; }
        string[] Dependencies { get; }
        IAuthorInfo Author { get; }
    }

    public interface IAuthorInfo
    {
        string Name { get; }
        string Twitter { get; }
        string Web { get; }
        string email { get; }
    }
}
