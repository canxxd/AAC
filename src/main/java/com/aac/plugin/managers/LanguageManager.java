package com.aac.plugin.managers;

import com.aac.plugin.AAC;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {
    
    private final AAC plugin;
    private final Map<String, FileConfiguration> languages;
    private String currentLanguage;
    
    public LanguageManager(AAC plugin) {
        this.plugin = plugin;
        this.languages = new HashMap<>();
        loadLanguages();
    }
    
    private void loadLanguages() {
        File languagesDir = new File(plugin.getDataFolder(), "languages");
        if (!languagesDir.exists()) {
            languagesDir.mkdirs();
        }
        
        String[] languageFiles = {
            "en.yml", "tr.yml"
        };
        
        for (String fileName : languageFiles) {
            File langFile = new File(languagesDir, fileName);
            if (!langFile.exists()) {
                saveDefaultLanguageFile(fileName);
            }
            
            FileConfiguration config = YamlConfiguration.loadConfiguration(langFile);
            languages.put(fileName.replace(".yml", ""), config);
        }
        
        currentLanguage = plugin.getConfigManager().getLanguage();
    }
    
    private void saveDefaultLanguageFile(String fileName) {
        File langFile = new File(plugin.getDataFolder(), "languages/" + fileName);
        
        try {
            InputStream inputStream = plugin.getResource("languages/" + fileName);
            if (inputStream != null) {
                Files.copy(inputStream, langFile.toPath());
            } else {
                createDefaultLanguageFile(langFile, fileName);
            }
        } catch (IOException e) {
            createDefaultLanguageFile(langFile, fileName);
        }
    }
    
    private void createDefaultLanguageFile(File file, String fileName) {
        FileConfiguration config = new YamlConfiguration();
        
        String langCode = fileName.replace(".yml", "");
        
        switch (langCode) {
            case "tr":
                setupTurkishMessages(config);
                break;
            case "en":
                setupEnglishMessages(config);
                break;
            default:
                setupEnglishMessages(config);
                break;
        }
        
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save language file" + fileName);
        }
    }
    
    private void setupTurkishMessages(FileConfiguration config) {
        config.set("prefix", "&6[AAC] ");
        config.set("no-permission", "&cBu komutu kullanmak için yetkiniz yok");
        config.set("player-not-found", "&cOyuncu bulunamadı");
        config.set("report-sent", "&aRapor başarıyla gönderildi");
        config.set("report-cooldown", "&cRapor göndermek için &6{time} &csaniye beklemelisiniz");
        config.set("cannot-report-self", "&cKendinizi raporlayamazsınız");
        
        config.set("gui.report-player.title", "&cOyuncu Raporla");
        config.set("gui.report-player.next-page", "&aSonraki Sayfa");
        config.set("gui.report-player.previous-page", "&cÖnceki Sayfa");
        config.set("gui.report-player.player-head-lore", "&7Raporlamak için tıklayın");
        
        config.set("gui.report-reasons.title", "&cRapor Sebebi Seçin");
        config.set("gui.report-reasons.hacking", "&cHile Kullanımı");
        config.set("gui.report-reasons.hacking-lore", "&7Oyuncu hile kullanıyor");
        config.set("gui.report-reasons.swearing", "&cKüfür");
        config.set("gui.report-reasons.swearing-lore", "&7Oyuncu küfür ediyor");
        config.set("gui.report-reasons.inappropriate-name", "&cUygunsuz İsim");
        config.set("gui.report-reasons.inappropriate-name-lore", "&7Oyuncunun ismi uygunsuz");
        config.set("gui.report-reasons.spam", "&cSpam");
        config.set("gui.report-reasons.spam-lore", "&7Oyuncu spam yapıyor");
        config.set("gui.report-reasons.griefing", "&cGriefing");
        config.set("gui.report-reasons.griefing-lore", "&7Oyuncu griefing yapıyor");
        config.set("gui.report-reasons.other", "&eÖzel Sebep");
        config.set("gui.report-reasons.other-lore", "&7Özel sebep girmek için tıklayın");
        
        config.set("gui.reports.title", "&6Raporlar");
        config.set("gui.reports.report-lore", 
            "&7Rapor Eden: &6{reporter}|&7Sebep: &6{reason}|&7Tarih: &6{date}|&7Saat: &6{time}|&7Son Mesajlar:|{messages}||&eDetaylı incelemek için tıklayın|&cSilmek için Shift + Sağ Tık");
        
        config.set("gui.aacwatch.title", "&6AAC Watch");
        config.set("gui.aacwatch.player-lore", "&7İzlemek için tıklayın");
        
        config.set("gui.glass-name", " ");
    }
    
    private void setupEnglishMessages(FileConfiguration config) {
        config.set("prefix", "&6[AAC] ");
        config.set("no-permission", "&cYou don't have permission to use this command");
        config.set("player-not-found", "&cPlayer not found");
        config.set("report-sent", "&aReport sent successfully");
        config.set("report-cooldown", "&cYou must wait &6{time} &cseconds before reporting again");
        config.set("cannot-report-self", "&cYou cannot report yourself");
        
        config.set("gui.report-player.title", "&cReport Player");
        config.set("gui.report-player.next-page", "&aNEXT PAGE");
        config.set("gui.report-player.previous-page", "&cPREVIOUS PAGE");
        config.set("gui.report-player.player-head-lore", "&7Click to report");
        
        config.set("gui.report-reasons.title", "&cSelect Report Reason");
        config.set("gui.report-reasons.hacking", "&cHacking");
        config.set("gui.report-reasons.hacking-lore", "&7Player is using hacks");
        config.set("gui.report-reasons.swearing", "&cSwearing");
        config.set("gui.report-reasons.swearing-lore", "&7Player is swearing");
        config.set("gui.report-reasons.inappropriate-name", "&cInappropriate Name");
        config.set("gui.report-reasons.inappropriate-name-lore", "&7Player has inappropriate name");
        config.set("gui.report-reasons.spam", "&cSpam");
        config.set("gui.report-reasons.spam-lore", "&7Player is spamming");
        config.set("gui.report-reasons.griefing", "&cGriefing");
        config.set("gui.report-reasons.griefing-lore", "&7Player is griefing");
        config.set("gui.report-reasons.other", "&eCustom Reason");
        config.set("gui.report-reasons.other-lore", "&7Click to enter custom reason");
        
        config.set("gui.reports.title", "&6Reports");
        config.set("gui.reports.report-lore", 
            "&7Reporter: &6{reporter}|&7Reason: &6{reason}|&7Date: &6{date}|&7Time: &6{time}|&7Recent Messages:|{messages}||&eClick to inspect|&cShift + Right Click to delete");
        
        config.set("gui.aacwatch.title", "&6AAC Watch");
        config.set("gui.aacwatch.player-lore", "&7Click to watch");
        
        config.set("gui.glass-name", " ");
    }
    
    public String getMessage(String key) {
        FileConfiguration config = languages.get(currentLanguage);
        if (config == null) {
            config = languages.get("en");
        }
        
        String message = config.getString(key, key);
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public String getMessage(String key, Map<String, String> placeholders) {
        String message = getMessage(key);
        
        if (placeholders != null) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        
        return message;
    }
    
    public void setLanguage(String language) {
        if (languages.containsKey(language)) {
            this.currentLanguage = language;
        }
    }
    
    public String getCurrentLanguage() {
        return currentLanguage;
    }
    
    public void reloadLanguages() {
        languages.clear();
        loadLanguages();
        plugin.getLogger().info("Language files reloaded successfully");
    }
}