package data;

public class AppiumData {

    public enum ButtonStatus {
        ON, OFF, INVALID
    }

    public enum Attributes {
        UID,
        accessibilityContainer,
        accessible,
        enabled,
        frame,
        label,
        name,
        rect,
        selected,
        type,
        value,
        visible,
        wdAccessibilityContainer,
        wdAccessible,
        wdEnabled,
        wdFrame,
        wdLabel,
        wdName,
        wdRect,
        wdSelected,
        wdType,
        wdUID,
        wdValue,
        wdVisible
    }

    public enum SystemBundles {
        Activity("com.apple.Fitness"),
        App_Store("com.apple.AppStore"),
        Books("com.apple.iBooks"),
        Calculator("com.apple.calculator"),
        Calendar("com.apple.mobilecal"),
        Camera("com.apple.camera"),
        Clips("com.apple.clips"),
        Clock("com.apple.mobiletimer"),
        Compass("com.apple.compass"),
        Contacts("com.apple.MobileAddressBook"),
        FaceTime("com.apple.facetime"),
        Files("com.apple.DocumentsApp"),
        Find_Friends("com.apple.mobileme.fmf1"),
        Find_iPhone("com.apple.mobileme.fmip1"),
        GarageBand("com.apple.mobilegarageband"),
        Health("com.apple.Health"),
        Home("com.apple.Home"),
        iCloud_Drive("com.apple.iCloudDriveApp"),
        iMovie("com.apple.iMovie"),
        iTunes_Store("com.apple.MobileStore"),
        iTunes_U("com.apple.itunesu"),
        Mail("com.apple.mobilemail"),
        Maps("com.apple.Maps"),
        Messages("com.apple.MobileSMS"),
        Measure("com.apple.measure"),
        Music("com.apple.Music"),
        News("com.apple.news"),
        Notes("com.apple.mobilenotes"),
        Phone("com.apple.mobilephone"),
        Photos("com.apple.mobileslideshow"),
        Photo_Booth("com.apple.Photo-Booth"),
        Podcasts("com.apple.podcasts"),
        Reminders("com.apple.reminders"),
        Safari("com.apple.mobilesafari"),
        Settings("com.apple.Preferences"),
        Shortcuts("is.workflow.my.app"),
        Stocks("com.apple.stocks"),
        Tips("com.apple.tips"),
        TV("com.apple.tv"),
        Videos("com.apple.videos"),
        Voice_Memos("com.apple.VoiceMemos"),
        Wallet("com.apple.Passbook"),
        Watch("com.apple.Bridge"),
        Weather("com.apple.weather");

        private String bundleId;

        SystemBundles(String bundleId) {
            this.bundleId = bundleId;
        }

        public String getBundleId() {
            return this.bundleId;
        }
    }

}
