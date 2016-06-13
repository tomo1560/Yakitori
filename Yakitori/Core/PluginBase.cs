namespace Yakitori.Core
{
    public abstract class PluginBase
    {
        public abstract string PluginID { get; }

        private bool _enabled = false;

        public bool enabled
        {
            get
            {
                return _enabled;
            }
        }

        public void SetEnable()
        {
            if (!enabled && OnEnable())
            {
                _enabled = true;
                // ToDo: Logに有効化できた旨を表示
            }
            else
            {
                // ToDo: Logに有効化できなかった旨を表示
            }
        }

        public void SetDisable()
        {
            if (enabled && OnDisable())
            {
                _enabled = false;
                // ToDo: Logに無効化できた旨を表示
            }
            else
            {
                // ToDo: Logに無効化できなかった旨を表示
            }
        }

        private bool OnEnable()
        {
            return true;
        }

        private bool OnDisable()
        {
            return true;
        }
    }
}
