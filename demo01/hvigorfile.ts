import { appTasks } from '@ohos/hvigor-ohos-plugin';

export default {
    system: appTasks,  /* Built-in plugin of Hvigor. It cannot be modified. */
    plugins:[] ,        /* Custom plugin to extend the functionality of Hvigor. */


    buildConfig: {
        arktsCompilerOptions: {
            enableArkTSDeprecationCheck: false // ✅ 关闭 deprecated 检查
        }
    }

}
