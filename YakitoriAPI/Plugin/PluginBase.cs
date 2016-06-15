using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Yakitori.API.Plugin
{
    public abstract class PluginBase
    {
        private IPluginInfo _info;

        public IPluginInfo Info
        {
            get
            {
                return _info;
            }
        }

        public void Init(IPluginInfo info)
        {
            _info = info;
        }

        public abstract void Initialize();
    }
}
