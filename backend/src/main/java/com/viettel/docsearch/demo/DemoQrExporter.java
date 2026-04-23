package com.viettel.docsearch.demo;

import com.viettel.docsearch.config.AppProperties;
import com.viettel.docsearch.domain.Document;
import com.viettel.docsearch.repo.DocumentRepository;
import com.viettel.docsearch.service.QrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class DemoQrExporter {

    private static final Logger log = LoggerFactory.getLogger(DemoQrExporter.class);

    private final AppProperties props;
    private final DocumentRepository docs;
    private final QrService qr;

    public DemoQrExporter(AppProperties props, DocumentRepository docs, QrService qr) {
        this.props = props;
        this.docs = docs;
        this.qr = qr;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void exportIfEnabled() throws Exception {
        if (!props.isExportQr()) return;
        Path dir = Paths.get("seed-output", "qr");
        Files.createDirectories(dir);
        List<Document> list = docs.findAll();
        for (Document d : list) {
            byte[] png = qr.pngFor(d.getId());
            Files.write(dir.resolve(d.getId() + ".png"), png);
        }
        log.info("Exported {} QR PNGs to {}", list.size(), dir.toAbsolutePath());
    }
}
