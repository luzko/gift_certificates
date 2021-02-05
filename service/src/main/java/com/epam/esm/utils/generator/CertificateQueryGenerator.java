package com.epam.esm.utils.generator;

import com.epam.esm.specification.Specification;
import com.epam.esm.specification.impl.GetCertificateByPartDescription;
import com.epam.esm.specification.impl.GetCertificateByPartName;
import com.epam.esm.specification.impl.GetCertificateByTagName;
import com.epam.esm.specification.impl.GetExistCertificate;
import com.epam.esm.specification.impl.SortCertificateASC;
import com.epam.esm.specification.impl.SortCertificateDESC;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CertificateQueryGenerator {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String TAG = "tag";
    private static final String DATE = "date";
    private static final String CREATE_DATE = "createDate";
    private static final String SORT = "sort";
    private static final String SORT_ORDER = "sortOrder";
    private static final String DESC = "DESC";
    private static final String COMMA = ",";

    private List<Specification> specifications;

    public List<Specification> generate(Map<String, String> parameters) {
        specifications = new ArrayList<>();
        generateSpecifications(parameters);
        return specifications;
    }

    private void generateSpecifications(Map<String, String> parameters) {
        specifications.add(new GetExistCertificate());
        parameters.keySet().forEach(key -> {
                    switch (key) {
                        case NAME:
                            specifications.add(new GetCertificateByPartName(parameters.get(key)));
                            break;
                        case DESCRIPTION:
                            specifications.add(new GetCertificateByPartDescription(parameters.get(key)));
                            break;
                        case TAG:
                            addTagName(parameters.get(key));
                            break;
                        case SORT:
                            boolean isDESC = DESC.equalsIgnoreCase(parameters.get(SORT_ORDER));
                            addOrder(parameters.get(key), isDESC);
                            break;
                        default:
                            break;
                    }
                }
        );
    }

    private void addTagName(String tag) {
        String[] tagNames = tag.split(COMMA);
        for (String tagName : tagNames) {
            specifications.add(new GetCertificateByTagName(tagName));
        }
    }

    private void addOrder(String order, boolean isDESC) {
        String[] orderNames = order.split(COMMA);
        for (String orderName : orderNames) {
            if (orderName.equalsIgnoreCase(NAME)) {
                specifications.add(isDESC ? new SortCertificateDESC(orderName) : new SortCertificateASC(orderName));
            } else if (orderName.equalsIgnoreCase(DATE)) {
                specifications.add(isDESC ? new SortCertificateDESC(CREATE_DATE) : new SortCertificateASC(CREATE_DATE));
            }
        }
    }
}
