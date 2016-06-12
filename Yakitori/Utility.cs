using System;
namespace Yakitori
{
    public class Utility
    {
        private static DateTime UNIX_EPOCH = new DateTime(1970, 1, 1, 0, 0, 0, 0);

        public static long GetEpochTime(DateTime targetTime)
        {
            targetTime = targetTime.ToUniversalTime();
            return (long)(targetTime - Utility.UNIX_EPOCH).TotalSeconds;
        }
    }
}

