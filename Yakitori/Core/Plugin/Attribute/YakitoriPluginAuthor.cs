using System;

namespace Yakitori.Core.Plugin.Attribute
{
    [AttributeUsage(AttributeTargets.Assembly)]
    class YakitoriPluginAuthor : System.Attribute
    {
        public readonly string Author;

        public YakitoriPluginAuthor(string author)
        {
            Author = author;
        }
    }
}
