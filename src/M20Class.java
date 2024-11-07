public class M20Class {

    public native byte CPSC1900Connect(byte paramByte, String paramString);
    public native byte CPSC1900EmbosserCleanLineData();
    public native byte CPSC1900EmbosserDownloadLineData(String name, int posX, int posy);
    public native byte CPSC1900FeedCard();
    public native byte CPSC1900EmbosserEmbossLines(boolean postTopper);
    public native byte CPSC1900TopperPressCard(boolean waitTempReady,byte secondToWait);
    public native byte CPSC1900EjectCard(byte ejectTO);
    public native byte CPSC1900Disconnect();
    public native byte CPSC1900DeviceReset();

    public String ConvertToHex(byte value){
        return String.format("0x%02X", value);
    }

    static {
        try {
            System.load("C:\\Windows\\SysWOW64\\M20_One.dll");
            System.out.println("Library loaded successfully.");
        } catch (Exception e) {
            System.err.println("Error loading library: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        M20Class m = new M20Class();
        
        byte connectResult = m.CPSC1900Connect((byte) 0x30, "null");
        String hexString = m.ConvertToHex(connectResult);
        System.out.println("Returned hex code: " + hexString);

        if (hexString.equals("0x00")) {
            byte clean_data = m.CPSC1900EmbosserCleanLineData();
            String cld_hex = m.ConvertToHex(clean_data);

            if (cld_hex.equals("0x00")) {               
                byte result = m.CPSC1900EmbosserDownloadLineData("BRIANKARIUKI", 420, 310);
                String edd_hex = m.ConvertToHex(result);

                if (edd_hex.equals("0x00")) {
                    byte feed_result = m.CPSC1900FeedCard(); 
                    String feed_hex = m.ConvertToHex(feed_result);

                    if (feed_hex.equals("0x00")) {
                        byte emb = m.CPSC1900EmbosserEmbossLines(false);
                        String emb_hex = m.ConvertToHex(emb);

                        if (emb_hex.equals("0x00")) {
                            byte topp = m.CPSC1900TopperPressCard(true, (byte) 20);
                            String top_hex = m.ConvertToHex(topp);
                        
                            if (top_hex.equals("0x00")) {
                                byte CPSC1900_IO_DOWN = 0x30;
                                byte eject = m.CPSC1900EjectCard(CPSC1900_IO_DOWN);
                                String ej_hex = m.ConvertToHex(eject);

                                if (ej_hex.equals("0x00")){
                                    byte disc = m.CPSC1900Disconnect();
                                    String disc_hex = m.ConvertToHex(disc);
                                    System.out.println("Disconnection Successful. Code: " + disc_hex);
                                } else {
                                    System.out.println("Card ejection failed. Code: " + ej_hex);
                                }
                            } else {
                                System.out.println("Topping failed. Code: " + top_hex);
                            }
                        } else {
                            System.out.println("EmbosserEmbossLines failed. Code: " + emb_hex);
                        }
                    } else {
                        System.out.println("Card not fed. Code: " + feed_hex);
                    }
                } else {
                    System.out.println("CPSC1900EmbosserDownloadLineData failed. Card not printed. Code: " + edd_hex);
                }
            } else {
                System.out.println("EmbossCleanLineData failed. Code: " + cld_hex);
            }
        } else {
            System.out.println("Printer connection failed. Code: " + hexString);
        }  
        
        byte resetDevice = m.CPSC1900DeviceReset();
        String res_hex = m.ConvertToHex(resetDevice);
        System.out.println("Device has been reset. Code: " + res_hex);
    }
}


